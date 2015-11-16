package com.theironyard.controllers;

import com.theironyard.entities.Grocery;
import com.theironyard.entities.User;
import com.theironyard.services.GroceryRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.util.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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
    public String home(HttpSession session, Model model, String search, String category, @RequestParam(defaultValue = "0") int page) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "login";
        }

        PageRequest pr = new PageRequest(page, 10);

        Page p;

        if (category != null) {
            p = groceries.findByCategoryOrderByNameAsc(pr, category);
        } else if (search != null) {
            p = groceries.searchByName(pr, search);
        } else {
            p = groceries.findAll(pr);
        }

        model.addAttribute("nextPage", page+1);
        model.addAttribute("category", category);
        model.addAttribute("groceries", p);
        model.addAttribute("showNext", p.hasNext());
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
            Double quantity,
            String quantityType,
            String category
    ) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        User user = users.findOneByUsername(username);

        Grocery grocery = groceries.findOne(id);
        if (name != "") {
            grocery.name = name;
        }

        if (brand != "") {
            grocery.brand = brand;
        }

        if (quantity != null) {
            grocery.quantity = quantity;
        }

        if (quantityType != null) {
            grocery.quantityType = quantityType;
        }

        if (category != null) {
            grocery.category = category;
        }

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
