/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package b2s.compare.clipboard;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.text.JTextComponent;
import org.netbeans.api.diff.Diff;
import org.netbeans.api.diff.DiffView;
import org.netbeans.api.diff.StreamSource;
import org.netbeans.api.editor.EditorRegistry;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.cookies.EditorCookie;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

public final class CompareWithClipboardAction implements ActionListener {

    private final EditorCookie context;

    public CompareWithClipboardAction(EditorCookie context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        JTextComponent comp = EditorRegistry.lastFocusedComponent();
        String text = comp.getSelectedText();
        boolean hasTextSelected = text != null && text.trim().length() > 0;

        Clipboard clipboard = Lookup.getDefault().lookup(Clipboard.class);
        Transferable contents = clipboard.getContents(null);
        boolean hasTextInClipboard = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if ( hasTextInClipboard && hasTextSelected ) {
          try {
            StreamSource selectedText = toStreamSource("Selected Text", comp.getSelectedText());
            StreamSource clipboardContents = toStreamSource("Clipboard Contents", (String)contents.getTransferData(DataFlavor.stringFlavor));
            DiffView view = Diff.getDefault().createDiff(selectedText, clipboardContents);

            DialogDescriptor descriptor = new DialogDescriptor(
                    view.getComponent(),
                    "Compare to Clipboard",
                    true,
                    new Object[]{DialogDescriptor.OK_OPTION},
                    DialogDescriptor.OK_OPTION,
                    DialogDescriptor.DEFAULT_ALIGN,
                    null,
                    null
            );
            Frame window = WindowManager.getDefault().getMainWindow();

            Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
            dialog.setSize(640, 480);
            dialog.setLocationRelativeTo(window);
            dialog.setVisible(true);
          } catch (UnsupportedFlavorException ex){
            ex.printStackTrace();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }

    }

    private StreamSource toStreamSource(String title, String contents) {
        return StreamSource.createSource(title, title, "text/plain", new StringReader(contents));
    }
}
