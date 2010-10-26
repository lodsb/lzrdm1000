/*
 * Project: JJack - Java bridge API for the JACK Audio Connection Kit
 * Class:   JJackSystem
 * Version: 0.3
 *
 * Date:    2007-12-30
 *
 * Licensed under the GNU Lesser General Public License (LGPL).
 * This comes with NO WARRANTY. See file LICENSE for details.
 *
 * Author:  Jens Gulden
 */


/**
 * Wrapper class for de.gulden.framework.jjack.JJackSystem.main(args).
 * This class only redirects the invokation to the actual JJack main class,
 * which allows to invoke the program as "[java] [classapth] JJack".
 *  
 * @author  Jens Gulden
 * @version  0.3
 * @see  de.gulden.framework.jjack.JJackSystem
 */
public class JJackSystem {

    // ------------------------------------------------------------------------
    // --- static method                                                    ---
    // ------------------------------------------------------------------------

    /**
     * Application entry method.
     * Redirects the invokation to the actual JJack main class de.gulden.application.jjack.JJack.
     *  
     * @param args command line parameters
     * @see  de.gulden.framework.jjack.JJackSystem#main
     */
    public static void main(String[] args) throws Throwable {
        de.gulden.framework.jjack.JJackSystem.main(args);
    }

} // end JJackSystem
