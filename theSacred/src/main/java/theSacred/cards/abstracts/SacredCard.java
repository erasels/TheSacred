package theSacred.cards.abstracts;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import theSacred.TheSacred;
import theSacred.characters.SacredCharacter;
import theSacred.patches.cards.CardENUMs;
import theSacred.util.CardInfo;
import theSacred.util.TextureLoader;
import theSacred.util.UC;

import static theSacred.TheSacred.makeID;

public abstract class SacredCard extends CustomCard {
    protected CardStrings cardStrings;
    protected String img;

    protected boolean upgradesDescription;

    protected int baseCost;

    protected boolean upgradeCost;
    protected boolean upgradeDamage;
    protected boolean upgradeBlock;
    protected boolean upgradeMagic;

    protected int costUpgrade;
    protected int damageUpgrade;
    protected int blockUpgrade;
    protected int magicUpgrade;

    protected boolean baseExhaust;
    protected boolean upgExhaust;
    protected boolean baseInnate;
    protected boolean upgInnate;

    protected boolean upgradeBurst;
    protected boolean upgradeRetain;
    protected boolean upgradeEthereal;
    protected boolean upgradeMultiDmg;

    public int baseMagicNumber2;
    public int magicNumber2;
    public boolean isMagicNumber2Modified;

    public int baseShowNumber;
    public int showNumber;
    public boolean isShowNumberModified;


    public SacredCard(CardInfo cardInfo, boolean upgradesDescription) {
        this(SacredCharacter.Enums.COLOR_SACRED, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public SacredCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(makeID(cardName), "", (String) null, cost, "", cardType, color, rarity, target);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        img = TextureLoader.getAndLoadCardTextureString(cardName, cardType);
        this.textureImg = img;
        loadCardImage(textureImg);

        this.rarity = autoRarity();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.originalName = cardStrings.NAME;
        this.name = originalName;

        this.baseCost = cost;

        this.upgradesDescription = upgradesDescription;

        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;

        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;

        upgradeBurst = false;
        upgradeRetain = false;
        upgradeEthereal = false;

        if(cardName.toLowerCase().contains("strike")) {
            tags.add(CardTags.STRIKE);
        }
        if(cardName.toLowerCase().contains("barrier")) {
            tags.add(CardENUMs.BARRIER);
        }
        if(this instanceof AlignedCard) {
            tags.add(CardENUMs.ALIGNED);
        }

        InitializeCard();
    }

    //Methods meant for constructor use
    public void setDamage(int damage) {
        this.setDamage(damage, 0);
    }

    public void setBlock(int block) {
        this.setBlock(block, 0);
    }

    public void setMagic(int magic) {
        this.setMagic(magic, 0);
    }

    public void setCostUpgrade(int costUpgrade) {
        this.costUpgrade = costUpgrade;
        this.upgradeCost = true;
    }

    public void setExhaust(boolean exhaust) {
        this.setExhaust(exhaust, exhaust);
    }

    public void setDamage(int damage, int damageUpgrade) {
        this.baseDamage = this.damage = damage;
        if (damageUpgrade != 0) {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgrade;
        }
    }

    public void setBlock(int block, int blockUpgrade) {
        this.baseBlock = this.block = block;
        if (blockUpgrade != 0) {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgrade;
        }
    }

    public void setMagic(int magic, int magicUpgrade) {
        this.baseMagicNumber = this.magicNumber = magic;
        if (magicUpgrade != 0) {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgrade;
        }
    }

    public void setExhaust(boolean baseExhaust, boolean upgExhaust) {
        this.baseExhaust = baseExhaust;
        this.upgExhaust = upgExhaust;
        this.exhaust = baseExhaust;
    }

    public void setInnate(boolean baseInnate, boolean upgInnate) {
        this.baseInnate = baseInnate;
        this.isInnate = baseInnate;
        this.upgInnate = upgInnate;
    }

    public void setBurst(boolean upgradeToBurst) {
        if(upgradeToBurst) {
            upgradeBurst = true;
        } else {
            tags.add(CardENUMs.BURST);
        }
    }

    public void setRetain(boolean upgradeToRetain) {
        if(upgradeToRetain) {
            upgradeRetain = true;
        } else {
            selfRetain = true;
        }
    }

    public void setEthereal(boolean upgradeToEthereal) {
        if(upgradeToEthereal) {
            upgradeEthereal = true;
        } else {
            isEthereal = true;
        }
    }

    public void setMultiDamage(boolean upgradeMulti) {
        if(upgradeMulti) {
            upgradeMultiDmg = true;
        } else {
            this.isMultiDamage = true;
        }
    }

    private CardRarity autoRarity() {
        String packageName = this.getClass().getPackage().getName();

        String directParent;
        if (packageName.contains(".")) {
            directParent = packageName.substring(1 + packageName.lastIndexOf("."));
        } else {
            directParent = packageName;
        }
        switch (directParent) {
            case "common":
                return CardRarity.COMMON;
            case "uncommon":
                return CardRarity.UNCOMMON;
            case "rare":
                return CardRarity.RARE;
            case "basic":
                return CardRarity.BASIC;
            default:
                TheSacred.logger.info("Automatic Card rarity resulted in SPECIAL, input: " + directParent);
                return CardRarity.SPECIAL;
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof SacredCard) {
            card.rawDescription = this.rawDescription;
            ((SacredCard) card).upgradesDescription = this.upgradesDescription;

            ((SacredCard) card).baseCost = this.baseCost;

            ((SacredCard) card).upgradeCost = this.upgradeCost;
            ((SacredCard) card).upgradeDamage = this.upgradeDamage;
            ((SacredCard) card).upgradeBlock = this.upgradeBlock;
            ((SacredCard) card).upgradeMagic = this.upgradeMagic;

            ((SacredCard) card).costUpgrade = this.costUpgrade;
            ((SacredCard) card).damageUpgrade = this.damageUpgrade;
            ((SacredCard) card).blockUpgrade = this.blockUpgrade;
            ((SacredCard) card).magicUpgrade = this.magicUpgrade;

            ((SacredCard) card).baseExhaust = this.baseExhaust;
            ((SacredCard) card).upgExhaust = this.upgExhaust;
            ((SacredCard) card).baseInnate = this.baseInnate;
            ((SacredCard) card).upgInnate = this.upgInnate;

            ((SacredCard) card).upgradeMultiDmg = this.upgradeMultiDmg;
            ((SacredCard) card).upgradeRetain = this.upgradeRetain;
            ((SacredCard) card).upgradeEthereal = this.upgradeEthereal;
            ((SacredCard) card).upgradeBurst = this.upgradeBurst;

            ((SacredCard) card).baseMagicNumber2 = this.baseMagicNumber2;
            ((SacredCard) card).magicNumber2 = this.magicNumber2;
            ((SacredCard) card).baseShowNumber = this.baseShowNumber;
            ((SacredCard) card).showNumber = this.showNumber;
        }

        return card;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();

            if (this.upgradesDescription)
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;

            if (upgradeCost) {
                int diff = this.baseCost - this.cost; //positive if cost is reduced

                this.upgradeBaseCost(costUpgrade);
                this.cost -= diff;
                this.costForTurn -= diff;
                if (cost < 0)
                    cost = 0;

                if (costForTurn < 0)
                    costForTurn = 0;
            }

            if (upgradeDamage)
                this.upgradeDamage(damageUpgrade);

            if (upgradeBlock)
                this.upgradeBlock(blockUpgrade);

            if (upgradeMagic)
                this.upgradeMagicNumber(magicUpgrade);

            if (baseExhaust ^ upgExhaust) //different
                this.exhaust = upgExhaust;

            if (baseInnate ^ upgInnate) //different
                this.isInnate = upgInnate;

            if(upgradeBurst) {
                tags.add(CardENUMs.BURST);
            }

            if(upgradeRetain) {
                selfRetain = true;
            }

            if(upgradeEthereal) {
                isEthereal = true;
            }

            if(upgradeMultiDmg) {
                this.isMultiDamage = true;
            }

            this.initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if((this.hasTag(CardENUMs.BURST) && UC.anonymousCheckBurst()) || this.hasTag(CardENUMs.ALIGNED) && UC.isAligned()) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR;
        }
    }

    public void InitializeCard() {
        FontHelper.cardDescFont_N.getData().setScale(1.0f);
        this.initializeTitle();
        this.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.magicNumber2 = baseMagicNumber2;
        this.isMagicNumber2Modified = false;
        this.showNumber = baseShowNumber;
        this.isShowNumberModified = false;
    }

    @Override
    public void applyPowers() {
        this.applyPowersToMN2();
        this.applyPowersToSN();
        super.applyPowers();
    }

    private void applyPowersToMN2() {
        this.isMagicNumber2Modified = magicNumber2 != baseMagicNumber2;
    }
    private void applyPowersToSN() {
        this.isShowNumberModified = showNumber != baseShowNumber;
    }
}