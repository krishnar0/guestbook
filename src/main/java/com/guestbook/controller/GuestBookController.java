package com.guestbook.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guestbook.GuestbooktestApplication;
import com.guestbook.dao.GuestRoleDAO;
import com.guestbook.dao.GuestUserDAO;
import com.guestbook.model.GuestBook;
import com.guestbook.model.GuestRole;
import com.guestbook.model.GuestUser;
import com.guestbook.model.UserRole;
import com.guestbook.service.GuestBookDetailsService;
import com.guestbook.utils.WebUtils;

@Controller
public class GuestBookController {
	
	public static final String uploadingDir = System.getProperty("user.dir") + "\\uploadingDir\\";
	private static Logger  logger = LogManager.getLogger(GuestBookController.class);
	
	@Autowired
	private GuestBookDetailsService guestBook;
	
	
	private GuestUser guestUser;
	
	
	private List<String> guestRole;
	
	@Autowired
	GuestUserDAO guestUserDao;
	
	@Autowired
	GuestRoleDAO guestRoleDao;
	
	public void GuestbookController(GuestBookDetailsService guestBook) {

		Assert.notNull(guestBook, "Guestbook must not be null!");
		this.guestBook = guestBook;
	}
	
	@RequestMapping(value = "/guestBook", method = {RequestMethod.GET, RequestMethod.POST})
    public String userInfo(Model model, Principal principal) {
 
        // After user login successfully.
        String userName = principal.getName();
 
        logger.info("user Name", () -> userName);
 
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        guestUser=(GuestUser)guestUserDao.findUserAccount(userName);
        guestRole=(List<String>)guestRoleDao.getRoleNames(guestUser.getUserId());
        
        if(guestRole.get(0).equals("ROLE_ADMIN")) {
        	model.addAttribute("guestBookEntries", guestBook.allGuestBookEntries());
            model.addAttribute("roleInfo",guestRole);
        }else {
        	model.addAttribute("guestBookEntries", guestBook.userGuestBookEntries(guestUser.getUserId()));
            model.addAttribute("roleInfo",guestRole);
        }	
        return "guestEntriesPage";
    }
	
	@RequestMapping("/addEntry")  
    public String showform(Model m){ 
    	m.addAttribute("command", new GuestBook());
    	m.addAttribute("userId", guestUser.getUserId());
    	return "addGuestEntry"; 
    }  
	
	@PostMapping(value="/saveOrUpdate")  
    public String save(@ModelAttribute("guestEntry") GuestBook guestEntry ,@RequestParam(required = false) MultipartFile file,@RequestParam(required = false) String inputType, @RequestParam(required = false) String submit, Principal principal, RedirectAttributes redirectAttributes){  
		String userName = principal.getName();
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
        guestUser=(GuestUser)guestUserDao.findUserAccount(userName);
        
        
        if(submit.equalsIgnoreCase("submit")) {
        	guestEntry.setUserId(guestUser.getUserId());
        	if(inputType.equalsIgnoreCase("Text")) { 
            	guestEntry.setGuestBookDesc(guestEntry.getGuestBookDesc());
            	
            }else {
            	if (file.isEmpty()) {
                    redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                    return "addGuestEntry";
                }
                
                try {

                    // Get the file and save it somewhere
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadingDir + file.getOriginalFilename());
                    Files.write(path, bytes);

                    guestEntry.setGuestBookDesc(file.getOriginalFilename());
                    guestEntry.setGuestBookImage(bytes);
                    redirectAttributes.addFlashAttribute("message",
                            "You successfully uploaded '" + file.getOriginalFilename() + "'");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            
            
    		guestBook.addGuestBook(guestEntry);  
        }
        
        return "redirect:/guestBook";  
    }  
	
	@RequestMapping(value = "/approve/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	
	String approveGuestBook(@PathVariable Optional<Long> id) {

		return id.map(it -> {

			guestBook.approveGuestBook(it);
			return "redirect:/guestBook";

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@RequestMapping(value = "/reject/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	String rejectGuestBook(@PathVariable Optional<Long> id) {

		return id.map(it -> {

			guestBook.rejectGuestBook(it);
			return "redirect:/guestBook";

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	@DeleteMapping(path = "/guestbook/{id}")
	String deleteGuestBook(@PathVariable Optional<Long> id) {

		return id.map(it -> { 

			guestBook.deleteGuestBook(it);
			return "redirect:/guestBook";

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
