package cc.isotopestudio.bookquest.util;

import java.util.ArrayList;

public class JsonConverter {
    public static String toJson(String minecraftMessage) {
        return toJson(minecraftMessage, null, null, null, null);
    }

    public static String toJson(String minecraftMessage, String hoverEventType, String hoverEventValue, String clickEventType, String clickEventValue) {
        minecraftMessage = minecraftMessage.replaceAll("&", "§");
        ArrayList<JsonMessagePart> jsonParts = new ArrayList<JsonMessagePart>();
        String result = "";
//        String result = "[";
        for (String part : ("§f" + minecraftMessage).split("§")) {
            if (part.length() != 0)
                if (part.charAt(0) == 'l' || part.charAt(0) == 'L')
                    if (jsonParts.size() > 0) {
                        if (jsonParts.get(jsonParts.size() - 1).getMessage().length() == 0) {
                            jsonParts.get(jsonParts.size() - 1).setBold(true);
                            jsonParts.get(jsonParts.size() - 1).setMessage(jsonParts.get(jsonParts.size() - 1).getMessage() + part.substring(1));
                            continue;
                        }

                        jsonParts.add(new JsonMessagePart(part.substring(1),
                                jsonParts.get(jsonParts.size() - 1).getColor(),
                                true,
                                jsonParts.get(jsonParts.size() - 1).isItalic(),
                                jsonParts.get(jsonParts.size() - 1).isUnderlined(),
                                jsonParts.get(jsonParts.size() - 1).isStrikethrough(),
                                jsonParts.get(jsonParts.size() - 1).isOfuscated(),
                                hoverEventType,
                                hoverEventValue,
                                clickEventType,
                                clickEventValue));
                    } else
                        jsonParts.add(new JsonMessagePart(part.substring(1), "\"white\"", true, false, false, false, false));

                else if (part.charAt(0) == 'o' || part.charAt(0) == 'O')
                    if (jsonParts.size() > 0) {
                        if (jsonParts.get(jsonParts.size() - 1).getMessage().length() == 0) {
                            jsonParts.get(jsonParts.size() - 1).setItalic(true);
                            jsonParts.get(jsonParts.size() - 1).setMessage(jsonParts.get(jsonParts.size() - 1).getMessage() + part.substring(1));
                            continue;
                        }

                        jsonParts.add(new JsonMessagePart(part.substring(1),
                                jsonParts.get(jsonParts.size() - 1).getColor(),
                                jsonParts.get(jsonParts.size() - 1).isBold(),
                                true,
                                jsonParts.get(jsonParts.size() - 1).isUnderlined(),
                                jsonParts.get(jsonParts.size() - 1).isStrikethrough(),
                                jsonParts.get(jsonParts.size() - 1).isOfuscated(),
                                hoverEventType,
                                hoverEventValue,
                                clickEventType,
                                clickEventValue));
                    } else
                        jsonParts.add(new JsonMessagePart(part.substring(1), "\"white\"", false, true, false, false, false));

                else if (part.charAt(0) == 'n' || part.charAt(0) == 'N')
                    if (jsonParts.size() > 0) {
                        if (jsonParts.get(jsonParts.size() - 1).getMessage().length() == 0) {
                            jsonParts.get(jsonParts.size() - 1).setUnderlined(true);
                            jsonParts.get(jsonParts.size() - 1).setMessage(jsonParts.get(jsonParts.size() - 1).getMessage() + part.substring(1));
                            continue;
                        }

                        jsonParts.add(new JsonMessagePart(part.substring(1),
                                jsonParts.get(jsonParts.size() - 1).getColor(),
                                jsonParts.get(jsonParts.size() - 1).isBold(),
                                jsonParts.get(jsonParts.size() - 1).isItalic(),
                                true,
                                jsonParts.get(jsonParts.size() - 1).isStrikethrough(),
                                jsonParts.get(jsonParts.size() - 1).isOfuscated(),
                                hoverEventType,
                                hoverEventValue,
                                clickEventType,
                                clickEventValue));
                    } else
                        jsonParts.add(new JsonMessagePart(part.substring(1), "\"white\"", false, false, true, false, false));

                else if (part.charAt(0) == 'm' || part.charAt(0) == 'M')
                    if (jsonParts.size() > 0) {
                        if (jsonParts.get(jsonParts.size() - 1).getMessage().length() == 0) {
                            jsonParts.get(jsonParts.size() - 1).setStrikethrough(true);
                            jsonParts.get(jsonParts.size() - 1).setMessage(jsonParts.get(jsonParts.size() - 1).getMessage() + part.substring(1));
                            continue;
                        }

                        jsonParts.add(new JsonMessagePart(part.substring(1),
                                jsonParts.get(jsonParts.size() - 1).getColor(),
                                jsonParts.get(jsonParts.size() - 1).isBold(),
                                jsonParts.get(jsonParts.size() - 1).isItalic(),
                                jsonParts.get(jsonParts.size() - 1).isUnderlined(),
                                true,
                                jsonParts.get(jsonParts.size() - 1).isOfuscated(),
                                hoverEventType,
                                hoverEventValue,
                                clickEventType,
                                clickEventValue));
                    } else
                        jsonParts.add(new JsonMessagePart(part.substring(1), "\"white\"", false, false, false, true, false));

                else if (part.charAt(0) == 'k' || part.charAt(0) == 'K')
                    if (jsonParts.size() > 0) {
                        if (jsonParts.get(jsonParts.size() - 1).getMessage().length() == 0) {
                            jsonParts.get(jsonParts.size() - 1).setOfuscated(true);
                            jsonParts.get(jsonParts.size() - 1).setMessage(jsonParts.get(jsonParts.size() - 1).getMessage() + part.substring(1));
                            continue;
                        }

                        jsonParts.add(new JsonMessagePart(part.substring(1),
                                jsonParts.get(jsonParts.size() - 1).getColor(),
                                jsonParts.get(jsonParts.size() - 1).isBold(),
                                jsonParts.get(jsonParts.size() - 1).isItalic(),
                                jsonParts.get(jsonParts.size() - 1).isUnderlined(),
                                jsonParts.get(jsonParts.size() - 1).isStrikethrough(),
                                true,
                                hoverEventType,
                                hoverEventValue,
                                clickEventType,
                                clickEventValue));
                    } else
                        jsonParts.add(new JsonMessagePart(part.substring(1), "\"white\"", false, false, false, false, true));

                else if (part.charAt(0) == 'r' || part.charAt(0) == 'R') {
                    if (jsonParts.size() > 0 && jsonParts.get(jsonParts.size() - 1).getMessage().length() == 0) {
                        jsonParts.get(jsonParts.size() - 1).setColor("\"white\"");
                        jsonParts.get(jsonParts.size() - 1).setBold(false);
                        jsonParts.get(jsonParts.size() - 1).setItalic(false);
                        jsonParts.get(jsonParts.size() - 1).setUnderlined(false);
                        jsonParts.get(jsonParts.size() - 1).setStrikethrough(false);
                        jsonParts.get(jsonParts.size() - 1).setOfuscated(false);
                        jsonParts.get(jsonParts.size() - 1).setMessage(jsonParts.get(jsonParts.size() - 1).getMessage() + part.substring(1));
                        continue;
                    }

                    jsonParts.add(new JsonMessagePart(part.substring(1),
                            hoverEventType,
                            hoverEventValue,
                            clickEventType,
                            clickEventValue));
                } else
                    jsonParts.add(new JsonMessagePart(part.substring(1), getJsonColor(part.charAt(0)),
                            hoverEventType,
                            hoverEventValue,
                            clickEventType,
                            clickEventValue));
        }

        for (JsonMessagePart jsonM : jsonParts)
            if (jsonM.getMessage().length() != 0)
                result += jsonM.toString() + ",";


//        return result.substring(0, result.length() - 1) + "]";
        return result.substring(0, result.length() - 1);
    }

    public static String getJsonColor(char chatcolor) {
        switch (chatcolor) {
            case '0':
                return "\"black\"";
            case '1':
                return "\"dark_blue\"";
            case '2':
                return "\"dark_green\"";
            case '3':
                return "\"dark_aqua\"";
            case '4':
                return "\"dark_red\"";
            case '5':
                return "\"dark_purple\"";
            case '6':
                return "\"gold\"";
            case '7':
                return "\"gray\"";

            case '8':
                return "\"dark_gray\"";
            case '9':
                return "\"blue\"";
            case 'a':
                return "\"green\"";
            case 'b':
                return "\"aqua\"";
            case 'c':
                return "\"red\"";
            case 'd':
                return "\"light_purple\"";
            case 'e':
                return "\"yellow\"";
            case 'f':
                return "\"white\"";
        }
        return "\"white\"";
    }

    private static class JsonMessagePart {
        private String message;
        private String color;
        private boolean bold;
        private boolean italic;
        private boolean underlined;
        private boolean strikethrough;
        private boolean ofuscated;
        private String hoverEvent;
        private String clickEvent;

        public JsonMessagePart(String message) {
            this.message = message;
            this.color = "\"white\"";
            this.bold = false;
            this.italic = false;
            this.underlined = false;
            this.strikethrough = false;
            this.ofuscated = false;

            this.insertEvents(null, null, null, null);
        }

        public JsonMessagePart(String message, String color) {
            this(message);
            this.color = color;
        }

        public JsonMessagePart(String message, String color, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean ofuscated) {
            this(message, color);
            this.bold = bold;
            this.italic = italic;
            this.underlined = underlined;
            this.strikethrough = strikethrough;
            this.ofuscated = ofuscated;
        }

        public JsonMessagePart(String message, String hoverEventType, String hoverEventValue, String clickEventType, String clickEventValue) {
            this(message);
            this.insertEvents(hoverEventType, hoverEventValue, clickEventType, clickEventValue);
        }

        public JsonMessagePart(String message, String color, String hoverEventType, String hoverEventValue, String clickEventType, String clickEventValue) {
            this(message, color);
            this.insertEvents(hoverEventType, hoverEventValue, clickEventType, clickEventValue);
        }

        public JsonMessagePart(String message, String color, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean ofuscated, String hoverEventType, String hoverEventValue, String clickEventType, String clickEventValue) {
            this(message, color, bold, italic, underlined, strikethrough, ofuscated);

            this.insertEvents(hoverEventType, hoverEventValue, clickEventType, clickEventValue);
        }

        private void insertEvents(String hoverEventAction, String hoverEventValue, String clickEventAction, String clickEventValue) {
            if (hoverEventAction == null || hoverEventValue == null)
                this.hoverEvent = "{}";
            else
                this.hoverEvent = "{\"action\":\"" + hoverEventAction + "\", \"value\":" + hoverEventValue + "}";

            if (clickEventAction == null || clickEventValue == null)
                this.clickEvent = "{}";
            else
                this.clickEvent = "{\"action\":\"" + clickEventAction + "\", \"value\":" + clickEventValue + "}";
        }

        public String toString() {
            return "{\"text\":\"" + this.message + "\", \"color\":" + this.color + ", \"bold\":" + this.bold + ", \"italic\":"
                    + this.italic + ", \"underlined\":" + this.underlined + ", \"strikethrough\":" + this.strikethrough
                    + ", \"ofuscated\":" + this.ofuscated + ", " + "\"hoverEvent\":" + this.hoverEvent + ", \"clickEvent\":"
                    + this.clickEvent + "}";
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public boolean isBold() {
            return bold;
        }

        public void setBold(boolean bold) {
            this.bold = bold;
        }

        public boolean isItalic() {
            return italic;
        }

        public void setItalic(boolean italic) {
            this.italic = italic;
        }

        public boolean isUnderlined() {
            return underlined;
        }

        public void setUnderlined(boolean underlined) {
            this.underlined = underlined;
        }

        public boolean isStrikethrough() {
            return strikethrough;
        }

        public void setStrikethrough(boolean strikethrough) {
            this.strikethrough = strikethrough;
        }

        public boolean isOfuscated() {
            return ofuscated;
        }

        public void setOfuscated(boolean ofuscated) {
            this.ofuscated = ofuscated;
        }
    }
}
