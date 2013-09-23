/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is GURPS Character Sheet.
 *
 * The Initial Developer of the Original Code is Richard A. Wilkes.
 * Portions created by the Initial Developer are Copyright (C) 1998-2013 the
 * Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * ***** END LICENSE BLOCK ***** */

package com.trollworks.gcs.menu.edit;

import static com.trollworks.gcs.menu.edit.RandomizeNameCommand_LS.*;

import com.trollworks.gcs.character.SheetWindow;
import com.trollworks.gcs.character.names.USCensusNames;
import com.trollworks.ttk.annotation.LS;
import com.trollworks.ttk.annotation.Localized;
import com.trollworks.ttk.menu.Command;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;

import javax.swing.JMenuItem;

@Localized({
				@LS(key = "MALE", msg = "Male"),
				@LS(key = "FEMALE", msg = "Female"),
				@LS(key = "TITLE", msg = "Generate Random {0} Name"),
})
/** Provides the "Generate Random Name" command. */
public class RandomizeNameCommand extends Command {
	/** The action command this command will issue. */
	public static final String					CMD_GENERATE_RANDOM_MALE_NAME	= "GenerateRandomMaleName";																							//$NON-NLS-1$
	/** The action command this command will issue. */
	public static final String					CMD_GENERATE_RANDOM_FEMALE_NAME	= "GenerateRandomFemaleName";																							//$NON-NLS-1$
	/** The male {@link RandomizeNameCommand}. */
	public static final RandomizeNameCommand	MALE_INSTANCE					= new RandomizeNameCommand(MALE, CMD_GENERATE_RANDOM_MALE_NAME, KeyEvent.VK_V, Command.SHIFTED_COMMAND_MODIFIER);
	/** The female {@link RandomizeNameCommand}. */
	public static final RandomizeNameCommand	FEMALE_INSTANCE					= new RandomizeNameCommand(FEMALE, CMD_GENERATE_RANDOM_FEMALE_NAME, KeyEvent.VK_I, Command.SHIFTED_COMMAND_MODIFIER);

	private RandomizeNameCommand(String type, String cmd, int keyCode, int modifiers) {
		super(MessageFormat.format(TITLE, type), cmd, keyCode, modifiers);
	}

	@Override
	public void adjustForMenu(JMenuItem item) {
		setEnabled(getActiveWindow() instanceof SheetWindow);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		((SheetWindow) getActiveWindow()).getCharacter().getDescription().setName(USCensusNames.INSTANCE.getFullName(this == MALE_INSTANCE));
	}
}
