package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by jessicahuffstutler on 11/12/15.
 */
@Controller
public class GroceryTrackerController {
    @Autowired
    UserRepository users;

    @Autowired
    GroceryRepository groceries;

    @RequestMapping("/")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "login";
        }
        model.addAttribute("groceries", groceries.findAll());
        return "home";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session) throws Exception {
        session.setAttribute("username", username);

        User user = users.findOneByUsername(username);
        if (user == null) {
            user = new User();
            user.username = username;
            user.password = PasswordHash.createHash(password);
            users.save(user);
        } else if (!PasswordHash.validatePassword(password, user.password)) {
            throw new Exception("Wrong password");
        }
        return "redirect:/";
    }

    @RequestMapping("/add-grocery")
    public String addGrocery(
            HttpSession session,
            String name,
            String brand,
            double quantity,
            String quantityType,
            String category
    ) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        User user = users.findOneByUsername(username);

        Grocery grocery = new Grocery();
        grocery.name = name;
        grocery.brand = brand;
        grocery.quantity = quantity;
        grocery.quantityType = quantityType;
        grocery.category = category;
        grocery.user = user;
        groceries.save(grocery);

        return "redirect:/";
    }

    @RequestMapping("/options")
    public String options(
            HttpSession session,
            Model model,
            Integer id
    ) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        User user = users.findOneByUsername(username);

        model.addAttribute("groceries", groceries.findOne(id));
        model.addAttribute("id", id);
        return "edit";
    }

    @RequestMapping("/edit-grocery")
    public String editGrocery(
            HttpSession session,
            Integer id,
            String name,
            String brand,
            double quantity,
            String quantityType,
            String category
    ) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        User user = users.findOneByUsername(username);

        Grocery grocery = groceries.findOne(id);
        grocery.name = name;
        grocery.brand = brand;
        grocery.quantity = quantity;
        grocery.quantityType = quantityType;
        grocery.category = category;
        grocery.user = user;
        groceries.save(grocery);

        return "redirect:/";
    }

    @RequestMapping("delete-grocery")
    public String deleteGrocery(Integer id) {
        groceries.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
