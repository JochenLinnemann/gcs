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

package com.trollworks.gcs.menu.item;

import static com.trollworks.gcs.menu.item.CopyToTemplateCommand_LS.*;

import com.trollworks.gcs.library.LibraryWindow;
import com.trollworks.gcs.template.TemplateWindow;
import com.trollworks.ttk.annotation.LS;
import com.trollworks.ttk.annotation.Localized;
import com.trollworks.ttk.menu.Command;
import com.trollworks.ttk.widgets.outline.OutlineModel;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

@Localized({
				@LS(key = "COPY_TO_TEMPLATE", msg = "Copy To Template"),
})
/** Provides the "Copy To Template" command. */
public class CopyToTemplateCommand extends Command {
	/** The action command this command will issue. */
	public static final String					CMD_COPY_TO_TEMPLATE	= "CopyToTemplate";			//$NON-NLS-1$
	/** The singleton {@link CopyToTemplateCommand}. */
	public static final CopyToTemplateCommand	INSTANCE				= new CopyToTemplateCommand();

	private CopyToTemplateCommand() {
		super(COPY_TO_TEMPLATE, CMD_COPY_TO_TEMPLATE, KeyEvent.VK_T, SHIFTED_COMMAND_MODIFIER);
	}

	@Override
	public void adjustForMenu(JMenuItem item) {
		Window window = getActiveWindow();
		if (window instanceof LibraryWindow) {
			setEnabled(((LibraryWindow) window).getOutline().getModel().hasSelection() && TemplateWindow.getTopTemplate() != null);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Window window = getActiveWindow();
		if (window instanceof LibraryWindow) {
			OutlineModel outlineModel = ((LibraryWindow) window).getOutline().getModel();
			if (outlineModel.hasSelection()) {
				TemplateWindow template = TemplateWindow.getTopTemplate();
				if (template != null) {
					template.addRows(outlineModel.getSelectionAsList(true));
				}
			}
		}
	}
}
