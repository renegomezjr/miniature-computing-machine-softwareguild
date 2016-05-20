/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.dvdlibrary;

import com.swcguild.dvdlibrary.ops.DvdLibraryController;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class DvdLibrary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        DvdLibraryController controller = ctx.getBean("controller", DvdLibraryController.class);
        controller.run();
    }
    
}
