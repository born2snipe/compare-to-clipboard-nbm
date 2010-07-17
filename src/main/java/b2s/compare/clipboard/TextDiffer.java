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

import java.io.IOException;
import java.io.StringReader;
import org.netbeans.api.diff.Diff;
import org.netbeans.api.diff.DiffView;
import org.netbeans.api.diff.StreamSource;

public class TextDiffer {
    public DiffView diff(String editor, String clipboard) {
        try {
            StreamSource selectedText = toStreamSource("Selected Text", editor);
            StreamSource clipboardContents = toStreamSource("Clipboard Contents", clipboard);
            return Diff.getDefault().createDiff(selectedText, clipboardContents);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private StreamSource toStreamSource(String title, String contents) {
        return StreamSource.createSource(title, title, "text/plain", new StringReader(contents));
    }
}
