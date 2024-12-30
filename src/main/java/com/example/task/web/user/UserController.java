package com.example.task.web.user;

import com.example.task.domain.auth.UserEntity;
import com.example.task.domain.auth.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntity userEntity;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDetailsService userDetailsService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String userList(Model model, @PageableDefault(size=5) Pageable pageable){
        Page<UserEntity> pageList = userService.findAllUser(pageable);
        List<UserEntity> userList = pageList.getContent();
        UserForm form = new UserForm();

        model.addAttribute("page", pageList);
        model.addAttribute("userList", userList);
        model.addAttribute("userForm", form);

        return "user/userList";
    }

    /* ユーザ新規登録 */
    @GetMapping("/regist")
    public String inputUser(Model model) {
        if (!model.containsAttribute("userForm")) {
            UserBaseForm userNewRegistForm = new UserNewRegistForm();
            model.addAttribute("userForm", userNewRegistForm);

            /* DBからユーザーIDを取得してきて、その次の番号を取得*/
            String maxUserId = userService.getMaxUserId();
            userNewRegistForm.setUserId(maxUserId);
        }

        return "user/userInfoInputForm";
    }

    @PostMapping("/regist")
    public String registUser(Model model, @Validated @ModelAttribute("userForm") UserNewRegistForm userNewRegistForm,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            redirectAttributes.addFlashAttribute("userForm", userNewRegistForm);

            // メッセージ
            String errorMessage = messageSource.getMessage("input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            // エラー時のリダイレクト
            return "redirect:/user/regist";
        }

        ConvertUser convertUser = new ConvertUser();

        UserEntity user = convertUser.registFormToEntity(userNewRegistForm);
        userService.registUser(user);

        return "redirect:/user";
    }

    @GetMapping("/invitations/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String invite(Model model, @PathVariable("userId") String userId) {
        /* ユーザ情報を取得*/
        UserEntity user = userService.getUserById(userId);

        model.addAttribute("email", user.getEmail());
        model.addAttribute("userId", userId);

        return "user/invite";
    }

//    @PostMapping("/invitations")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String invite(Model model, String userId, String email) {
//        /* ユーザ情報を取得*/
//        UserEntity user = userService.getUserById(userId);
//
//        userService.sendEmail(user);
//
//        return "redirect:/user";
//    }

    @GetMapping("/userRegist")
    public String registPassword(Model model, HttpServletRequest request){
        /* tokenの値からユーザ情報取得 */
        String token = request.getParameter("token");

        if(!model.containsAttribute("errorMessage")) {
            UserEntity user = userService.getUserByToken(token);

            model.addAttribute("userId", user.getUserId());
        }
        model.addAttribute("token", token);
        model.addAttribute("password", null);
        model.addAttribute("password2", null);

        return "user/registInfo";
    }

    @PostMapping("/userRegist")
    public String registPassword(Model model, String userId, String password, String password2,
                                 HttpServletRequest request,RedirectAttributes redirectAttributes){
        if(password == null || password.isEmpty() || password.length() < 8){
            // メッセージ
            String errorMessage = messageSource.getMessage("password.input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            String token = request.getParameter("token");

            // エラー時のリダイレクト
            return "redirect:/user/userRegist?token=" + token;

        }
        String enc_password = passwordEncoder.encode(password);
        // token削除して、パスワード設定
        userService.deleteToken(userId, enc_password);

        // 認証情報を手動でセットしてログイン状態にする
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken); // 認証情報をセット

        // セッションに認証情報を保存
        HttpSession session = request.getSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        String successMessage = messageSource.getMessage("registration.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        return "redirect:/";
    }

    /*
        管理者がユーザ情報を編集
     */
    @GetMapping("/changeUserInfo/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getChangeUserInfo(Model model, @PathVariable("userId") String userId){

        if(!model.containsAttribute("userForm")){
            /* ユーザ情報を取得*/
            UserEntity user = userService.getUserById(userId);

            ConvertUser convertUser = new ConvertUser();
            UserNewRegistForm userForm =  convertUser.entityToRegistForm(user);

            model.addAttribute("userForm", userForm);
        }


        return "user/eachUserInfoEditForm";
    }

    /*
        管理者がユーザ情報を編集
     */
    @PostMapping("/changeUserInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public String postChangeUserInfo(Model model, @Validated @ModelAttribute("userForm") UserNewRegistForm userNewRegistForm,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            redirectAttributes.addFlashAttribute("userForm", userNewRegistForm);

            // メッセージ
            String errorMessage = messageSource.getMessage("input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            // エラー時のリダイレクト
            return "redirect:/user/changeUserInfo/" + userNewRegistForm.getUserId();

        }

        /* ユーザー情報更新 */
        userService.modifyUserInfo(userNewRegistForm);

        String successMessage = messageSource.getMessage("edit.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("successMessage", successMessage);

        return "redirect:/";
    }


    /*
        自分の情報を編集
     */
    @GetMapping("/changeMyInfo")
    public String changeMyInfo(Model model){

        if(!model.containsAttribute("userForm")) {
            // ログインユーザIDを取得
            final String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            UserEntity user = userService.getUserById(userId);

            ConvertUser convertUser = new ConvertUser();
            UserForm userForm = convertUser.entityToForm(user);

            model.addAttribute("userForm", userForm);
        }

        return "user/userInfoEditForm";
    }
    /*
    自分の情報を編集
 */
    @PostMapping("/changeMyInfo")
    public String postChangeMyInfo(Model model, @Validated @ModelAttribute UserForm userForm,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            redirectAttributes.addFlashAttribute("userForm", userForm);

            // メッセージ
            String errorMessage = messageSource.getMessage("input.error", null, null);
            // 成功メッセージをFlashAttributesに追加
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            return "redirect:/user/changeMyInfo";
        }


        /* 自分のユーザー情報更新 */
        userService.modifyMyInfo(userForm);

        return "redirect:/";
    }

    // ユーザ削除
    @GetMapping("/delete/{userId}")
    public String delete(Model model, @PathVariable("userId") String userId,
                         RedirectAttributes redirectAttributes) {
        // 対象のユーザを削除（delete_flg = 1に）
        userService.deleteUser(userId);

        String delSuccessMessage = messageSource.getMessage("del.success", null, null);
        // 成功メッセージをFlashAttributesに追加
        redirectAttributes.addFlashAttribute("delSuccessMessage", delSuccessMessage);

        return "redirect:/user";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        // ログアウト成功時のメッセージを処理
        if (request.getParameter("logout") != null) {
            String logoutMessage = messageSource.getMessage("logout.success", null, null);
            model.addAttribute("logoutMessage", logoutMessage);
        }
        return "login";
    }

    @GetMapping("/logout")
    public String showLogoutForm() {

        return "logout";
    }

}
