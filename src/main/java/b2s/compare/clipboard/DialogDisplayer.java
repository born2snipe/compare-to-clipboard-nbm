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

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JPanel;
import org.netbeans.api.diff.DiffView;
import org.openide.DialogDescriptor;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

public class DialogDisplayer {
    private static final DiffPanel diffPanel = new DiffPanel();
    private static Dialog dialog;

    public void noDifferences() {
        DialogDescriptor descriptor = dialogDescriptor(NbBundle.getMessage(DialogDisplayer.class, "content.identical"));
        org.openide.DialogDisplayer.getDefault().notify(descriptor);
    }

    public void showDifferences(DiffView diffView) {
        Frame window = WindowManager.getDefault().getMainWindow();

        if (dialog == null) {
            DialogDescriptor descriptor = dialogDescriptor(diffPanel);
            dialog = org.openide.DialogDisplayer.getDefault().createDialog(descriptor);
            dialog.setSize(640, 480);
        }
        diffPanel.setDiffView(diffView);
        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);
    }

    private DialogDescriptor dialogDescriptor(Object message) {
        return new DialogDescriptor(
                message,
                NbBundle.getMessage(DialogDisplayer.class, "diff.dialog.title"),
                true,
                new Object[]{DialogDescriptor.OK_OPTION},
                DialogDescriptor.OK_OPTION,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                null
        );
    }

    private static class DiffPanel extends JPanel {
        public DiffPanel() {
            super(new BorderLayout());
        }

        public void setDiffView(DiffView diffView) {
            removeAll();
            add(diffView.getComponent(), BorderLayout.CENTER);
        }
    }
}
