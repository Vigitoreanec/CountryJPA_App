package org.top.countrydirectoryapp.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.countrydirectoryapp.model.*;


@Controller
@RequestMapping("countries")
public class CountryPagesController {

    private final CountryScenario countries;

    public CountryPagesController(CountryScenario countries) {
        this.countries = countries;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("countries", countries.listAll());
        return "country/list";
    }

    @GetMapping("new")
    public String getNewCountry(Model model) {
        model.addAttribute("country", new Country("", "", "", "", "", 0L, 0L));
        return "country/add-form";
    }

    @PostMapping("new")
    public String postNewCountry(Country country, RedirectAttributes ra) {
        countries.store(country);
        ra.addFlashAttribute("message", "Страна успешно добавлена");
        return "redirect:/countries";
    }

    @GetMapping("edit/{code}")
    public String getEditCountry(@PathVariable String code, Model model) {
        Country country = countries.get(code);
        String codeType = determineCodeType(code);
        model.addAttribute("codeType", codeType);
        model.addAttribute("country", country);
        return "country/edit-form";
    }

    @PostMapping("edit/{code}")
    public String postEditCountry(@PathVariable String code, Country country, RedirectAttributes ra) {

        country.setIsoAlpha2(code);
        // country.setIsoAlpha3(code);
        // country.setIsoNumeric(code);

        countries.edit(country);
        ra.addFlashAttribute("message", "Страна успешно отредактирована");
        return "redirect:/countries";
    }

    @GetMapping("delete/{code}")
    public String deleteCountry(@PathVariable String code, RedirectAttributes ra) {
        Country country = countries.get(code);
        countries.delete(code);
        ra.addFlashAttribute("message", country.getFullName() + " успешно удалена");
        return "redirect:/countries";
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public String handleCountryNotFound(CountryNotFoundException e, RedirectAttributes ra) {
        String errorMessage = "Страна с данным кодом не найдена";
        ra.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/countries";
    }

    @ExceptionHandler(DuplicatedCodeException.class)
    public String handleDuplicatedCode(DuplicatedCodeException e, RedirectAttributes ra) {
        String errorMessage = "Код дублируется";
        ra.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/countries";
    }

    @ExceptionHandler(InvalidCodeException.class)
    public String handleInvalidCode(InvalidCodeException e, RedirectAttributes ra) {
        String errorMessage = "Код не валидный";
        ra.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/countries";
    }

    private String determineCodeType(String code) {
        if (code == null) return "";
        if (code.matches("^[A-Z]{2}$")) return "alpha2";
        if (code.matches("^[A-Z]{3}$")) return "alpha3";
        if (code.matches("^\\d+$")) return "numeric";
        return "";
    }
}
