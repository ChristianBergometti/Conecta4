package Servicio;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import static java.lang.Thread.sleep;

public class LimpiarPantalla {
    public static void instantCleanScreen() throws InterruptedException {
    try {
            Robot pressbot = new Robot();
            pressbot.keyPress(KeyEvent.VK_CONTROL);
            pressbot.keyPress(KeyEvent.VK_L);
            pressbot.keyRelease(KeyEvent.VK_CONTROL);
            pressbot.keyRelease(KeyEvent.VK_L);
            sleep(300);
        } catch (AWTException ex) {
            System.out.println("" + ex.getMessage());

        }
  }

  public static void cleanScreen() throws InterruptedException {
    System.out.print("\nPresione Enter para Volver -->");
    new java.util.Scanner(System.in).nextLine();
    instantCleanScreen();
  }

  public static void cleanScreenContinuar() throws InterruptedException {
    System.out.print("\nPresione Enter para Continuar -->");
    new java.util.Scanner(System.in).nextLine();
    instantCleanScreen();
  }
}
