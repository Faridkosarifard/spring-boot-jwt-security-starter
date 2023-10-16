package com.example.demo.common;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/15/2023
 * Time: 10:19 PM
 */
public class MailHtmlContent {
    private static final MailHtmlContent MAIL_HTML_CONTENT = new MailHtmlContent();

    public MailHtmlContent() {
    }

    public static MailHtmlContent getInstance() {
        return MAIL_HTML_CONTENT;
    }

    public String verifyMailHtmlContent (String name, String family, String token){
        String html ="<html><head>\n" +
                "<style>\n" +
                "\n" +
                "  @import url(\"https://fonts.example.com/placeholder\");\n" +
                "  body {\n" +
                "    font-family: 'Scheherazade', serif;\n" +
                "  }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body><p dir=\"rtl\">\n" +
                "</p><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"table-layout:fixed;background-color:#f9f9f9\" id=\"bodyTable\">\n" +
                " <tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"padding-right:10px;padding-left:10px;\" align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperWebview\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top: 20px; padding-bottom: 20px; padding-right: 0px;\" align=\"right\" valign=\"middle\" class=\"webview\"> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperBody\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<p dir=\"rtl\">\n" +
                "    \n" +
                "\t\t\t\t\t\t\t\t\t\t\t</p><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableCard\" style=\"background-color:#fff;border-color:#e5e5e5;border-style:solid;border-width:0 1px 1px 1px;\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color:#0335fc;font-size:1px;line-height:3px\" class=\"topBorder\" height=\"3\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top: 60px; padding-bottom: 20px;\" align=\"center\" valign=\"middle\" class=\"emailLogo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"text-decoration:none\" target=\"_blank\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 20px;\" align=\"center\" valign=\"top\" class=\"imgHero\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"text-decoration:none\" target=\"_blank\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"\" src=\"https://example.com/logo.png\" style=\"width:80%;max-width:600px;height:auto;display:block;color: #f9f9f9;\" width=\"500\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr> <td style=\"padding-bottom: 5px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"mainTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h2 class=\"text\" style=\"color:#000;font-family:sans-serif;font-size:28px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:36px;text-transform:none;text-align:center;padding:0;margin:0\">سلام \""+ name + family +"\"</h2>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 30px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"subTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text\" style=\"color:#999;font-family:sans-serif;font-size:16px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:24px;text-transform:none;text-align:center;padding:0;margin:0\">ایمیل خود را تایید کنید</h4>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:20px;padding-right:20px\" align=\"center\" valign=\"top\" class=\"containtTable ui-sortable\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableDescription\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 20px;\" align=\"center\" valign=\"top\" class=\"description\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#666;font-family:sans-serif ;font-size:14px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:22px;text-transform:none;text-align:center;padding:0;margin:0\">از شما سپاسگزاریم که به خانواده ما پیوسته&zwnj;اید. این ایمیل جهت تایید ثبت نام شما ارسال شده است.\n" +
                "لطفاً ایمیل خود را تایید کنید تا بتوانید به پنل مدیریتی خود دسترسی داشته باشید. برای تایید آدرس ایمیل خود، روی دکمه زیر کلیک کنید.\n" +
                "\n" +
                "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableButton\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:20px;padding-bottom:20px\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color: #0335fc; padding: 12px 35px; border-radius: 50px;\" align=\"center\" class=\"ctaButton\"> <a href=\""+ token +"\" style=\"color:#fff;font-family:sans-serif;font-size:13px;font-weight:600;font-style:normal;letter-spacing:1px;line-height:20px;text-transform:uppercase;text-decoration:none;display:block\" target=\"_blank\" class=\"text\">تایید ایمیل</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"20\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" valign=\"middle\" style=\"padding-bottom: 40px;\" class=\"emailRegards\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<!-- Image and Link // -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" target=\"_blank\" style=\"text-decoration:none;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img mc:edit=\"signature\" src=\"https://example.com/signature.png\" alt=\"\" width=\"150\" border=\"0\" style=\"width:100%;\n" +
                "    max-width:150px; height:auto; display:block;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"space\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperFooter\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"footer\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\" align=\"center\" valign=\"top\" class=\"socialLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#facebook-link\" style=\"display:inline-block\" target=\"_blank\" class=\"facebook\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/facebook.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#twitter-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"twitter\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/twitter.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#pintrest-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"pintrest\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/pintrest.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#instagram-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"instagram\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/instagram.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#linkdin-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"linkdin\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/linkdin.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 10px 10px 5px;\" align=\"center\" valign=\"top\" class=\"brandInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:12px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:20px;text-transform:none;text-align:center;padding:0;margin:0\">©&nbsp;APP |Tehran, Iran</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 0px 10px 10px;\" align=\"center\" valign=\"top\" class=\"footerEmailInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:12px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:20px;text-transform:none;text-align:center;padding:0;margin:0\">If you have any quetions please contact us <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">support@mail.com.</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<br> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Unsubscribe</a> from our mailing lists</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\" align=\"center\" valign=\"top\" class=\"appLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#Play-Store-Link\" style=\"display: inline-block;\" target=\"_blank\" class=\"play-store\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/app/play-store.png\" style=\"height:auto;margin:5px;width:100%;max-width:120px\" width=\"120\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#App-Store-Link\" style=\"display: inline-block;\" target=\"_blank\" class=\"app-store\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/app/app-store.png\" style=\"height:auto;margin:5px;width:100%;max-width:120px\" width=\"120\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table></body></html>";
        return html;
    }

    public String forgetPasswordHtmlContent (String name, String family, String token){
        String html ="<html><head>\n" +
                "<style>\n" +
                "\n" +
                "  @import url(\"https://fonts.example.com/placeholder\");\n" +
                "  body {\n" +
                "    font-family: 'Scheherazade', serif;\n" +
                "  }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body><p dir=\"rtl\">\n" +
                "</p><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"table-layout:fixed;background-color:#f9f9f9\" id=\"bodyTable\">\n" +
                "\t<tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"padding-right:10px;padding-left:10px;\" align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperWebview\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top: 20px; padding-bottom: 20px; padding-right: 0px;\" align=\"right\" valign=\"middle\" class=\"webview\"> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperBody\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<p dir=\"rtl\">\n" +
                "    \n" +
                "\t\t\t\t\t\t\t\t\t\t\t</p><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableCard\" style=\"background-color:#fff;border-color:#e5e5e5;border-style:solid;border-width:0 1px 1px 1px;\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color:#0335fc;font-size:1px;line-height:3px\" class=\"topBorder\" height=\"3\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top: 60px; padding-bottom: 20px;\" align=\"center\" valign=\"middle\" class=\"emailLogo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"text-decoration:none\" target=\"_blank\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 20px;\" align=\"center\" valign=\"top\" class=\"imgHero\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"text-decoration:none\" target=\"_blank\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"\" src=\"https://example.com/logo.png\" style=\"width:80%;max-width:600px;height:auto;display:block;color: #f9f9f9;\" width=\"500\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr> <td style=\"padding-bottom: 5px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"mainTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h2 class=\"text\" style=\"color:#000;font-family:sans-serif;font-size:28px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:36px;text-transform:none;text-align:center;padding:0;margin:0\">سلام \""+ name + family +"\"</h2>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 30px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"subTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text\" style=\"color:#999;font-family:sans-serif;font-size:16px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:24px;text-transform:none;text-align:center;padding:0;margin:0\">ایمیل خود را تایید کنید</h4>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:20px;padding-right:20px\" align=\"center\" valign=\"top\" class=\"containtTable ui-sortable\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableDescription\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 20px;\" align=\"center\" valign=\"top\" class=\"description\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#666;font-family:sans-serif ;font-size:14px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:22px;text-transform:none;text-align:center;padding:0;margin:0\">از شما سپاسگزاریم که به خانواده ما پیوسته&zwnj;اید. این ایمیل جهت تایید ثبت نام شما ارسال شده است.\n" +
                "لطفاً ایمیل خود را تایید کنید تا بتوانید به پنل مدیریتی خود دسترسی داشته باشید. برای تایید آدرس ایمیل خود، روی دکمه زیر کلیک کنید.\n" +
                "\n" +
                "</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"tableButton\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:20px;padding-bottom:20px\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color: #0335fc; padding: 12px 35px; border-radius: 50px;\" align=\"center\" class=\"ctaButton\"> <a href=\""+ token +"\" style=\"color:#fff;font-family:sans-serif;font-size:13px;font-weight:600;font-style:normal;letter-spacing:1px;line-height:20px;text-transform:uppercase;text-decoration:none;display:block\" target=\"_blank\" class=\"text\">تایید ایمیل</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"20\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" valign=\"middle\" style=\"padding-bottom: 40px;\" class=\"emailRegards\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<!-- Image and Link // -->\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" target=\"_blank\" style=\"text-decoration:none;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img mc:edit=\"signature\" src=\"https://example.com/signature.png\" alt=\"\" width=\"150\" border=\"0\" style=\"width:100%;\n" +
                "    max-width:150px; height:auto; display:block;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"space\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperFooter\" style=\"max-width:600px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"footer\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\" align=\"center\" valign=\"top\" class=\"socialLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#facebook-link\" style=\"display:inline-block\" target=\"_blank\" class=\"facebook\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/facebook.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#twitter-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"twitter\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/twitter.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#pintrest-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"pintrest\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/pintrest.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#instagram-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"instagram\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/instagram.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#linkdin-link\" style=\"display: inline-block;\" target=\"_blank\" class=\"linkdin\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/social/light/linkdin.png\" style=\"height:auto;width:100%;max-width:40px;margin-left:2px;margin-right:2px\" width=\"40\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 10px 10px 5px;\" align=\"center\" valign=\"top\" class=\"brandInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:12px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:20px;text-transform:none;text-align:center;padding:0;margin:0\">©&nbsp;APP |Tehran, Iran</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 0px 10px 10px;\" align=\"center\" valign=\"top\" class=\"footerEmailInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:'Open Sans',Helvetica,Arial,sans-serif;font-size:12px;font-weight:400;font-style:normal;letter-spacing:normal;line-height:20px;text-transform:none;text-align:center;padding:0;margin:0\">If you have any quetions please contact us <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">support@mail.com.</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<br> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Unsubscribe</a> from our mailing lists</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:10px;padding-bottom:10px;padding-left:10px;padding-right:10px\" align=\"center\" valign=\"top\" class=\"appLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#Play-Store-Link\" style=\"display: inline-block;\" target=\"_blank\" class=\"play-store\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/app/play-store.png\" style=\"height:auto;margin:5px;width:100%;max-width:120px\" width=\"120\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#App-Store-Link\" style=\"display: inline-block;\" target=\"_blank\" class=\"app-store\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img alt=\"\" border=\"0\" src=\"http://email.aumfusion.com/vespro/img/app/app-store.png\" style=\"height:auto;margin:5px;width:100%;max-width:120px\" width=\"120\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td style=\"font-size:1px;line-height:1px\" height=\"30\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table></body></html>";
        return html;
    }
}
