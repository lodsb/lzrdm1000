/*
 * Project: JJack - Java bridge API for the JACK Audio Connection Kit
 * Class:   de.gulden.application.jjack.clients.Channel
 * Version: 0.3
 *
 * Date:    2007-12-30
 *
 * Licensed under the GNU Lesser General Public License (LGPL).
 * This comes with NO WARRANTY. See file LICENSE for details.
 *
 * Author:  Jens Gulden
 */

package de.gulden.application.jjack.clients;

import de.gulden.framework.jjack.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import java.nio.FloatBuffer;

/**
 * JJack example client: Selects one channel from a multi-channel input and
 * routes it to the mono output channel #0.
 * The typical operating scenario for this is to start a monitor chain
 * with selecting which channel to display by e.g. an Oscilloscope.
 *  
 * @author  Jens Gulden
 * @version  0.3
 */
public class Channel extends JJackClient implements ChangeListener {

    // ------------------------------------------------------------------------
    // --- field                                                            ---
    // ------------------------------------------------------------------------

    protected int channel = 0;


    // ------------------------------------------------------------------------
    // --- methods                                                          ---
    // ------------------------------------------------------------------------

    /**
     * Process multiple samples from input buffer to output buffer.
     * This is regularly called by the JACK daemon.
     *  
     * @param e event object with references to input buffer and output buffer.
     * @see  de.gulden.framework.jjack.JJackAudioProcessor#process(de.gulden.framework.jjack.JJackAudioEvent)
     */
    public void process(JJackAudioEvent e) {
        FloatBuffer in = e.getInput(getChannel());
        FloatBuffer out = e.getOutput(e.getMonoOutputPort());
        out.put(in);
    }

    /**
     */
    public int getChannel() {
        return channel;
    }

    /**
     */
    public void setChannel(int i) {
        if (channel != i) {
        	channel = i;
        	updateUI();
        }
    }

    /**
     * Synchronize user interface with parameter values.
     */
    public void updateUI() {
        if (gui != null) {
        	((JSpinner)gui).getModel().setValue(new Integer(getChannel()+1));
        }
    }

    /**
     *  
     * @see  javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        Integer value = (Integer)((JSpinner)e.getSource()).getModel().getValue();
        setChannel(value.intValue()-1);
    }

    /**
     * Creates the user interface.
     *  
     * @return  visible component with the user interface, or <code>null</code> if this is an invisible audio processor
     */
    protected JComponent createUI() {
        int inputChannelCount = JJackSystem.countPorts(INPUT);
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, inputChannelCount, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.addChangeListener(this);
        return spinner;
    }

} // end Channel
