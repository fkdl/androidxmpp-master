/**
 * $Revision$
 * $Date$
 *
 * Copyright 2003-2007 Jive Software.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.smackx.workgroup.ext.forms;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.xmlpull.v1.XmlPullParser;

public class WorkgroupForm extends IQ {

	/**
	 * An IQProvider for WebForm packets.
	 * 
	 * @author Derek DeMoro
	 */
	public static class InternalProvider implements IQProvider {

		public InternalProvider() {
			super();
		}

		@Override
		public IQ parseIQ(XmlPullParser parser) throws Exception {
			final WorkgroupForm answer = new WorkgroupForm();

			boolean done = false;
			while (!done) {
				final int eventType = parser.next();
				if (eventType == XmlPullParser.START_TAG) {
					// Parse the packet extension
					answer.addExtension(PacketParserUtils.parsePacketExtension(
							parser.getName(), parser.getNamespace(), parser));
				} else if (eventType == XmlPullParser.END_TAG) {
					if (parser.getName().equals(ELEMENT_NAME)) {
						done = true;
					}
				}
			}

			return answer;
		}
	}

	/**
	 * Element name of the packet extension.
	 */
	public static final String ELEMENT_NAME = "workgroup-form";

	/**
	 * Namespace of the packet extension.
	 */
	public static final String NAMESPACE = "http://jivesoftware.com/protocol/workgroup";

	@Override
	public String getChildElementXML() {
		final StringBuilder buf = new StringBuilder();

		buf.append("<").append(ELEMENT_NAME).append(" xmlns=\"")
				.append(NAMESPACE).append("\">");
		// Add packet extensions, if any are defined.
		buf.append(getExtensionsXML());
		buf.append("</").append(ELEMENT_NAME).append("> ");

		return buf.toString();
	}
}
