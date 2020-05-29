package view.ui;

/**
 * View Class which acts as a builder for the game's user interface
 *
 * This class is responsible for building the string output for the user.
 *
 * This UI consists of
 * - pre heading and pre info
 * - heading and sub heading
 * - body
 * - footer
 * - prompt
 *
 * This ui uses the builder pattern for ease of constructing a well-defined
 * ui structure throughout the game program.
 */
public class UserInterface
{
    /** UserInterface Fields **/
    private String preHeading;
    private String preInfo;
    private String heading;
    private String subHeading;
    private String body;
    private String footer;
    private String prompt;

    public static class Builder
    {
        private final UserInterface ui;

        /**
         * Constructor
         *
         * Initialises all components to a blank
         */
        public Builder()
        {
            ui = new UserInterface();

            ui.preHeading = "";
            ui.preInfo = "";
            ui.heading = "";
            ui.subHeading = "";
            ui.body = "";
            ui.footer = "";
            ui.prompt = "";
        }

        /** Adds a pre heading */
        public Builder withPreHeading(String preHeading)
        {
            ui.preHeading = preHeading;

            return this;
        }

        /** Adds a pre info */
        public Builder withPreInfo(String preInfo)
        {
            ui.preInfo = preInfo;
            return this;
        }

        /** Adds a heading */
        public Builder withHeading(String heading)
        {
            ui.heading = heading;
            return this;
        }

        /** Adds a sub heading */
        public Builder withSubHeading(String subHeading) {
            ui.subHeading = subHeading;
            return this;
        }

        /** Adds a body */
        public Builder withBody(String body) {
            ui.body = body;
            return this;
        }

        /** Adds a footer */
        public Builder withFooter(String footer)
        {
            ui.footer = footer;
            return this;
        }

        /** Adds a prompt */
        public Builder withPrompt(String prompt)
        {
            ui.prompt = prompt;
            return this;
        }

        /** Builders the ui based on called builder methods */
        public UserInterface build()
        {
            return ui;
        }

    }

    /**
     * Private Constructor
     *
     * This constructor is made private to disallow outside classes to call this constructor.
     * Since the building methods are within the Builder class, this implements it so that
     * the builder must be used to create the UI.
     */
    private UserInterface()
    {
        //Empty
    }

    /**
     * This reconstructs the string representation of the whole UI based on the components built from
     * the builder class.
     */
    public String getOutput()
    {
        // This is the width for the width of the banners/headings etc
        int bannerWidth = 150;

        // Use StringBuilder to append the built components together
        StringBuilder strBuilder = new StringBuilder("\n");

        // Only append if pre heading is not blank
        if(!preHeading.equals("") && !preInfo.equals(""))
        {
            // Appends the pre heading banner with dividers
            strBuilder.append(getSection(centreJustify(preHeading.replace("", " ").trim(), bannerWidth)));
            // Appends the pre information with dividers
            strBuilder.append(getSection(preInfo));
        }

        // Appends the heading banner with dividers - headings are compulsory as they display what the section is for
        strBuilder.append(getSection(centreJustify(heading.replace("", " ").trim(), bannerWidth)));

        // Only append if sub heading is not blank
        if(!subHeading.equals(""))
        {
            // Appends the sub heading banner with dividers
            strBuilder.append(centreJustify(subHeading, bannerWidth) + "\n");
        }

        // Appends the body with dividers - body is compulsory as they display the actual content
        strBuilder.append(getSection(body));

        // Only append if footer is not blank
        if(!footer.equals(""))
        {
            // Append the footer banner with dividers
            strBuilder.append(getSection(footer));
        }

        // Appends the prompt with dividers - prompts are compulsory as they notify the user that it is waiting for user input
        strBuilder.append(getSection(prompt));

        return strBuilder.toString();
    }

    /**
     * Method that creates a divider based on given width
     */
    private String getDivider(int dividerWidth)
    {
        return String.format("%" +dividerWidth+ "s", " ").replaceAll(" ", "~");
    }

    /**
     * Method to centre justify any given string
     */
    private String centreJustify(String banner, int bannerWidth)
    {
        int textWidth = banner.length();

        int leftPadding = (bannerWidth/2) + (textWidth/2);
        int rightPadding = bannerWidth;

        banner = String.format("%" +leftPadding+ "s", banner);
        banner = String.format("%-" +rightPadding+ "s", banner);

        return banner;
    }

    /**
     * Method that gets the ui component section and adding a divider
     */
    private String getSection(String section)
    {
        return getDivider(150) + "\n" + section + "\n";
    }
}