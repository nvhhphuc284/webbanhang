package com.example.demo.controller;
import com.example.demo.repositories.ICategoryRepository;
import com.example.demo.repositories.IXeRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.services.XeService;
import com.example.demo.entity.Xe;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import com.example.demo.services.CartService;
import com.example.demo.daos.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/xes")
@RequiredArgsConstructor
public class XeController {
    private final XeService xeService;
    private final CategoryService categoryService;
    private final CartService cartService;
    @GetMapping
    public String showAllXes(@NotNull Model model,
                               @RequestParam(defaultValue = "0")
                               Integer pageNo,
                               @RequestParam(defaultValue = "20")
                               Integer pageSize,
                               @RequestParam(defaultValue = "id")
                               String sortBy) {
        model.addAttribute("xes", xeService.getAllXes(pageNo,
                pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages",
                xeService.getAllXes(pageNo, pageSize, sortBy).size() / pageSize);
        model.addAttribute("categories",
                categoryService.getAllCategories());
        return "Xe/list";
    }

    @GetMapping("/add")
    public String addXeForm(@NotNull Model model) {
        model.addAttribute("xe", new Xe());
        model.addAttribute("categories",
                categoryService.getAllCategories());
        return "Xe/add";
    }
    @PostMapping("/add")
    public String addXe(
            @Valid @ModelAttribute("xe") Xe xe,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories",
                    categoryService.getAllCategories());
            return "xe/add";
        }
        xeService.addXe(xe);
        return "redirect:/xes";
    }
    //xoa
    @GetMapping("/delete/{id}")
    public String deleteXe(@PathVariable long id) {
        xeService.getXeById(id)
                .ifPresentOrElse(
                        xe -> xeService.deleteXeById(id),
                        () -> { throw new IllegalArgumentException("KHÔNG TÌM THẤY XE"); });
                            return "redirect:/xes";
                        }

    //sua
    @GetMapping("/edit/{id}")
    public String editXeForm(@NotNull Model model, @PathVariable long id)
    {
        var xe = xeService.getXeById(id);
        model.addAttribute("xe", xe.orElseThrow(() -> new IllegalArgumentException("KHÔNG TÌM THẤY XE")));
        model.addAttribute("category", categoryService.getAllCategories());
        return "xe/edit";
    }
    @PostMapping("/edit")
    public String editXe(@Valid @ModelAttribute("xe") Xe xe,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("category",
                    categoryService.getAllCategories());
            return "xe/edit";
        }
        xeService.updateXe(xe);
        return "redirect:/xes";
    }
    //tim kiem
    @GetMapping("/search")
    public String searchXe(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("xes", xeService.searchXe(keyword));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages",
                xeService
                        .getAllXes(pageNo, pageSize, sortBy)
                        .size() / pageSize);
        model.addAttribute("categories",
                categoryService.getAllCategories());
        return "xe/list";
    }

    //gio hang
        @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String name,
                            @RequestParam double price,
                            @RequestParam(defaultValue = "1") int quantity)
    {
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name, price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/xes";
    }
}
