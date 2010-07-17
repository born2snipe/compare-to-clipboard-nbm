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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.netbeans.api.diff.DiffView;
import org.openide.cookies.EditorCookie;

public final class CompareWithClipboardAction implements ActionListener {
    private SourceRetriever editorSelectedSourceRetriever;
    private final EditorCookie context;
    private SourceRetriever clipboardRetriever;
    private DialogDisplayer dialogDisplayer;
    private TextDiffer textDiffer;

    public CompareWithClipboardAction(EditorCookie context) {
        this.context = context;
        editorSelectedSourceRetriever = new EditorSelectedSourceRetriever();
        clipboardRetriever = new ClipboardSourceRetriever();
        dialogDisplayer = new DialogDisplayer();
        textDiffer = new TextDiffer();
    }

    public void actionPerformed(ActionEvent ev) {
        String editor = editorSelectedSourceRetriever.retrieve();
        String clipboard = clipboardRetriever.retrieve();
        if (hasText(editor) && hasText(clipboard)) {
            if (editor.equals(clipboard)) {
                dialogDisplayer.noDifferences();
            } else {
                DiffView diffView = textDiffer.diff(editor, clipboard);
                dialogDisplayer.showDifferences(diffView);
            }
        }
    }

    private boolean hasText(String text) {
        return text != null && text.trim().length() > 0;
    }

    void setEditorSourceRetriever(SourceRetriever editorSelectedSourceRetriever) {
        this.editorSelectedSourceRetriever = editorSelectedSourceRetriever;
    }

    void setClipboardSourceRetriever(SourceRetriever clipboardRetriever) {
        this.clipboardRetriever = clipboardRetriever;
    }

    void setDialogDisplayer(DialogDisplayer dialogDisplayer) {
        this.dialogDisplayer = dialogDisplayer;
    }

    void setTextDiffer(TextDiffer textDiffer) {
        this.textDiffer = textDiffer;
    }
}
