package theSacred.cards.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import theSacred.util.CardInfo;
import theSacred.util.UC;

public abstract class SacredChangeCard extends SacredCard {
    public boolean changed;

    public SacredChangeCard(CardInfo cardInfo, boolean upgradesDescription) {
        super(cardInfo, upgradesDescription);
    }

    public SacredChangeCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(color, cardName, cost, cardType, target, rarity, upgradesDescription);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();

        if (c instanceof SacredChangeCard) {
            ((SacredChangeCard) c).changed = this.changed;
        }
        return c;
    }

    public void updateChange() {
        doChange();
        changed = !changed;
        this.initializeTitle();
        this.initializeDescription();
    }

    public void doChange() {
        String b_name = this.name;
        String b_rawDesc = this.rawDescription;
        String b_img = this.textureImg;
        AbstractCard.CardType b_type = this.type;
        AbstractCard.CardTarget b_target = this.target;
        int b_damage = this.baseDamage;
        int b_block = this.baseBlock;
        int b_magic = this.baseMagicNumber;
        int b_sn = this.baseShowNumber;
        int b_m2 = this.baseMagicNumber2;
        int b_cost = this.baseCost;
        boolean b_upgDesc = this.upgradesDescription;
        boolean b_upgCost = this.upgradeCost;
        int b_costUprgade = this.costUpgrade;
        boolean b_udamage = this.upgradeDamage;
        boolean b_ublock = this.upgradeBlock;
        boolean b_umagic = this.upgradeMagic;
        int b_upgDamage = this.damageUpgrade;
        int b_upgBlock = this.blockUpgrade;
        int b_upgMagic = this.magicUpgrade;
        boolean b_bex = this.baseExhaust;
        boolean b_upgex = this.upgExhaust;
        boolean b_bin = this.baseInnate;
        boolean b_upgin = this.upgInnate;
        boolean b_upgmult = this.upgradeMultiDmg;
        boolean b_upgret = this.upgradeRetain;
        boolean b_upgeth = this.upgradeEthereal;

        this.name = cardsToPreview.name;
        this.rawDescription = cardsToPreview.rawDescription;
        this.textureImg = ((SacredChangeCard) cardsToPreview).textureImg;
        loadCardImage(this.textureImg);
        this.type = cardsToPreview.type;
        this.target = cardsToPreview.target;
        this.baseDamage = cardsToPreview.baseDamage;
        this.baseBlock = cardsToPreview.baseBlock;
        this.baseMagicNumber = cardsToPreview.baseMagicNumber;
        this.baseMagicNumber2 = ((SacredChangeCard) cardsToPreview).baseMagicNumber2;
        this.baseShowNumber = ((SacredChangeCard) cardsToPreview).baseShowNumber;
        this.baseCost = ((SacredChangeCard) cardsToPreview).baseCost;
        this.upgradesDescription = ((SacredChangeCard) cardsToPreview).upgradesDescription;
        this.upgradeCost = ((SacredChangeCard) cardsToPreview).upgradeCost;
        this.costUpgrade = ((SacredChangeCard) cardsToPreview).costUpgrade;
        this.upgradeDamage = ((SacredChangeCard) cardsToPreview).upgradeDamage;
        this.upgradeBlock = ((SacredChangeCard) cardsToPreview).upgradeBlock;
        this.upgradeMagic = ((SacredChangeCard) cardsToPreview).upgradeMagic;
        this.damageUpgrade = ((SacredChangeCard) cardsToPreview).damageUpgrade;
        this.blockUpgrade = ((SacredChangeCard) cardsToPreview).blockUpgrade;
        this.magicUpgrade = ((SacredChangeCard) cardsToPreview).magicUpgrade;
        this.baseExhaust = ((SacredChangeCard) cardsToPreview).baseExhaust;
        this.upgExhaust = ((SacredChangeCard) cardsToPreview).upgExhaust;
        this.baseInnate = ((SacredChangeCard) cardsToPreview).baseInnate;
        this.upgInnate = ((SacredChangeCard) cardsToPreview).upgInnate;
        this.upgradeMultiDmg = ((SacredChangeCard) cardsToPreview).upgradeMultiDmg;
        this.upgradeRetain = ((SacredChangeCard) cardsToPreview).upgradeRetain;
        this.upgradeEthereal = ((SacredChangeCard) cardsToPreview).upgradeEthereal;

        cardsToPreview.name = b_name;
        cardsToPreview.rawDescription = b_rawDesc;
        ((SacredChangeCard) cardsToPreview).textureImg = b_img;
        ((SacredChangeCard) cardsToPreview).loadCardImage(((SacredChangeCard) cardsToPreview).textureImg);
        cardsToPreview.type = b_type;
        cardsToPreview.target = b_target;
        cardsToPreview.baseDamage = b_damage;
        cardsToPreview.baseBlock = b_block;
        cardsToPreview.baseMagicNumber = b_magic;
        ((SacredChangeCard) cardsToPreview).baseMagicNumber2 = b_m2;
        ((SacredChangeCard) cardsToPreview).baseShowNumber = b_sn;
        ((SacredChangeCard) cardsToPreview).baseCost = b_cost;
        ((SacredChangeCard) cardsToPreview).upgradesDescription = b_upgDesc;
        ((SacredChangeCard) cardsToPreview).upgradeCost = b_upgCost;
        ((SacredChangeCard) cardsToPreview).costUpgrade = b_costUprgade;
        ((SacredChangeCard) cardsToPreview).upgradeDamage = b_udamage;
        ((SacredChangeCard) cardsToPreview).upgradeBlock = b_ublock;
        ((SacredChangeCard) cardsToPreview).upgradeMagic = b_umagic;
        ((SacredChangeCard) cardsToPreview).damageUpgrade = b_upgDamage;
        ((SacredChangeCard) cardsToPreview).blockUpgrade = b_upgBlock;
        ((SacredChangeCard) cardsToPreview).magicUpgrade = b_upgMagic;
        ((SacredChangeCard) cardsToPreview).baseExhaust = b_bex;
        ((SacredChangeCard) cardsToPreview).upgExhaust = b_upgex;
        ((SacredChangeCard) cardsToPreview).baseInnate = b_bin;
        ((SacredChangeCard) cardsToPreview).upgInnate = b_upgin;
        ((SacredChangeCard) cardsToPreview).upgradeMultiDmg = b_upgmult;
        ((SacredChangeCard) cardsToPreview).upgradeRetain = b_upgret;
        ((SacredChangeCard) cardsToPreview).upgradeEthereal = b_upgeth;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (!changed) {
            use1(abstractPlayer, abstractMonster);
        } else {
            use2(abstractPlayer, abstractMonster);
        }
    }

    protected abstract void use1(AbstractPlayer self, AbstractMonster target);

    protected abstract void use2(AbstractPlayer self, AbstractMonster target);

    //TODO: Test if this is wonky when upgrading mid-combat
    @Override
    public void upgrade() {
        super.upgrade();
        if (!upgraded) {
            cardsToPreview.upgrade();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "renderHand")
    public static class AlwaysRenderSwitchcardTip {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractPlayer __instance, SpriteBatch sb) {
            AbstractCard c = __instance.hoveredCard;
            if ((__instance.isDraggingCard || __instance.inSingleTargetMode) && c instanceof SacredChangeCard) {
                c.renderCardPreview(sb);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderHoverShadow");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class Switcher {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(UseCardAction __instance, AbstractCard ___targetCard) {
            if (___targetCard instanceof SacredChangeCard) {
                UC.atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        ((SacredChangeCard) ___targetCard).updateChange();
                        ___targetCard.superFlash();
                        isDone = true;
                    }
                });
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}
