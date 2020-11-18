package theSacred;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSacred.cards.abstracts.SacredCard;
import theSacred.cards.variables.InvokeAddition;
import theSacred.cards.variables.MagicNumber2;
import theSacred.cards.variables.ShowNumber;
import theSacred.characters.SacredCharacter;
import theSacred.mechanics.speed.AbstractSpeedTime;
import theSacred.relics.special.PurificationRod;
import theSacred.util.TextureLoader;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

@SpireInitializer
public class TheSacred implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PreStartGameSubscriber,
        PostUpdateSubscriber,
        PostBattleSubscriber {
    public static final Logger logger = LogManager.getLogger(TheSacred.class.getName());
    private static String modID;

    public static Properties theSacredSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    public static AbstractSpeedTime speedScreen;

    private static final String MODNAME = "The Sacred";
    private static final String AUTHOR = "erasels";
    private static final String DESCRIPTION = "TODO"; //TODO: Write character Description

    public static final Color SACRED_RED = CardHelper.getColor(60, 36, 36);

    private static final String ATTACK_SACRED_RED = "theSacredResources/images/512/bg_attack_immortal_red.png";
    private static final String SKILL_SACRED_RED = "theSacredResources/images/512/bg_skill_immortal_red.png";
    private static final String POWER_SACRED_RED = "theSacredResources/images/512/bg_power_immortal_red.png";

    private static final String ENERGY_ORB_SACRED_RED = "theSacredResources/images/512/card_immortal_red_orb.png";
    private static final String CARD_ENERGY_ORB = "theSacredResources/images/512/card_small_orb.png";

    private static final String ATTACK_SACRED_RED_PORTRAIT = "theSacredResources/images/1024/bg_attack_immortal_red.png";
    private static final String SKILL_SACRED_RED_PORTRAIT = "theSacredResources/images/1024/bg_skill_immortal_red.png";
    private static final String POWER_SACRED_RED_PORTRAIT = "theSacredResources/images/1024/bg_power_immortal_red.png";
    private static final String ENERGY_ORB_SACRED_RED_PORTRAIT = "theSacredResources/images/1024/card_immortal_red_orb.png";

    private static final String THE_SACRED_BUTTON = "theSacredResources/images/charSelect/CharacterButton.png";
    private static final String THE_SACRED_PORTRAIT = "theSacredResources/images/charSelect/CharacterPortraitBG.png";
    public static final String THE_SACRED_SHOULDER_1 = "theSacredResources/images/char/shoulder.png";
    public static final String THE_SACRED_SHOULDER_2 = "theSacredResources/images/char/shoulder2.png";
    public static final String THE_SACRED_CORPSE = "theSacredResources/images/char/corpse.png";

    public static final String BADGE_IMAGE = "theSacredResources/images/Badge.png";

    public static ArrayList<SacredCard> needles = new ArrayList<>();
    public static ArrayList<SacredCard> barriers = new ArrayList<>();

    public TheSacred() {
        BaseMod.subscribe(this);

        setModID("theSacred");

        logger.info("Creating the color " + SacredCharacter.Enums.COLOR_SACRED.toString());

        BaseMod.addColor(SacredCharacter.Enums.COLOR_SACRED, SACRED_RED, SACRED_RED, SACRED_RED,
                SACRED_RED, SACRED_RED, SACRED_RED, SACRED_RED,
                ATTACK_SACRED_RED, SKILL_SACRED_RED, POWER_SACRED_RED, ENERGY_ORB_SACRED_RED,
                ATTACK_SACRED_RED_PORTRAIT, SKILL_SACRED_RED_PORTRAIT, POWER_SACRED_RED_PORTRAIT,
                ENERGY_ORB_SACRED_RED_PORTRAIT, CARD_ENERGY_ORB);

        theSacredSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("theSacred", "theSacredConfig", theSacredSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        TheSacred immortalMod = new TheSacred();
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + SacredCharacter.Enums.THE_SACRED.toString());
        BaseMod.addCharacter(new SacredCharacter("the Sacred", SacredCharacter.Enums.THE_SACRED), THE_SACRED_BUTTON, THE_SACRED_PORTRAIT, SacredCharacter.Enums.THE_SACRED);
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, enablePlaceholder, settingsPanel, (label) -> {
        }, (button) -> {
            enablePlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig("theSacred", "theSacredConfig", theSacredSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
    }

    //Checks to make sure player is playing this character before running animations
    public static void runAnimation(String anim) {
        if (AbstractDungeon.player.chosenClass == SacredCharacter.Enums.THE_SACRED) {
            ((SacredCharacter) AbstractDungeon.player).runAnim(anim);
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        if(MathUtils.randomBoolean()) {
            runAnimation("winA");
        } else {
            runAnimation("winB");
        }
    }

    @Override
    public void receivePostUpdate() {
        if (speedScreen != null && speedScreen.isDone) {
            speedScreen = null;
        }
    }

    @Override
    public void receivePreStartGame() {
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new PurificationRod(), SacredCharacter.Enums.COLOR_SACRED);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new MagicNumber2());
        BaseMod.addDynamicVariable(new ShowNumber());
        BaseMod.addDynamicVariable(new InvokeAddition());

        new AutoAdd(getModID())
                .packageFilter("theSacred.cards")
                .notPackageFilter("theSacred.cards._deprecated")
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/cardStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/powerStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/relicStrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, getModID() + "Resources/localization/eng/eventStrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, getModID() + "Resources/localization/eng/potionStrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/characterStrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, getModID() + "Resources/localization/eng/orbStrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/eng/uiStrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, getModID() + "Resources/localization/eng/monsterStrings.json");
    }

    public static String[] invokeKeywords = new String[2];
    public static String[] fieldKeywords = new String[2];
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/keywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                if(keyword.PROPER_NAME.contains("Invoke")) {
                    invokeKeywords[0] = StringUtils.capitalize(keyword.NAMES[0].substring(keyword.NAMES[0].indexOf(":") + 1));
                    invokeKeywords[1] = keyword.DESCRIPTION;
                } else if(keyword.PROPER_NAME.contains("Field")) {
                    fieldKeywords[0] = StringUtils.capitalize(keyword.NAMES[0].substring(keyword.NAMES[0].indexOf(":") + 1));
                    fieldKeywords[1] = keyword.DESCRIPTION;
                }
            }
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static String makePath(String resourcePath) {
        return getModID() + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeVfxPath(String resourcePath) {
        return getModID() + "Resources/images/vfx/" + resourcePath;
    }

    public static String makeCharPath(String resourcePath) {
        return getModID() + "Resources/images/char/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }


    public static void setModID(String ID) {
        modID = ID;
    }

    public static String getModID() { // NO
        return modID;
    }
}
