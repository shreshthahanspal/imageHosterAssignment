package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;

import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ImageService imageService;

    /**
     * Method to create/ add comment on an image.
     *
     * @param imageId
     * @param title
     * @param text
     * @param session
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/image/{id}/{title}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable("id") Integer imageId, @PathVariable("title") String title,
                                @RequestParam("comment") String text, HttpSession session, Model model) throws IOException {
        Image image = imageService.getImage(imageId);
        Comment comment = new Comment();
        if (text == null || text.trim().length() == 0) {
            return "redirect:/images/" + imageId + "/" + image.getTitle();
        }
        User user = (User) session.getAttribute("loggeduser");
        comment.setUser(user);
        comment.setText(text);
        comment.setImage(image);
        comment.setCreatedDate(new Date());
        commentService.addComment(comment);
        return "redirect:/images";
    }
}
