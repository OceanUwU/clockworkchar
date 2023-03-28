package clockworkchar.cards;

import basemod.abstracts.CustomCard;
import clockworkchar.characters.TheClockwork;
import clockworkchar.patches.AttunedPatches;
import clockworkchar.util.CardArtRoller;

import static clockworkchar.ClockworkChar.makeImagePath;
import static clockworkchar.ClockworkChar.modID;
import static clockworkchar.util.Wiz.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractEasyCard extends CustomCard {

    protected final CardStrings cardStrings;

    public boolean part = false;
    public int extraAttunings = 0;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public int secondBlock;
    public int baseSecondBlock;
    public boolean upgradedSecondBlock;
    public boolean isSecondBlockModified;

    public int spinAmount;
    public int baseSpinAmount;
    public boolean upgradedSpinAmount;
    public boolean isSpinAmountModified;

    private boolean needsArtRefresh = false;

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, TheClockwork.Enums.CLOCKWORK_BROWN_COLOR);
    }

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.getAllCards() != null && !CardLibrary.getAllCards().isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString;

        switch (cardType) {
            case ATTACK:
            case POWER:
            case SKILL:
                textureString = makeImagePath("cards/" + cardName + ".png");
                break;
            default:
                textureString = makeImagePath("ui/missing.png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    protected void applyPowersToBlock() {
        if (baseSecondBlock > -1) {
            secondBlock = baseSecondBlock;

            int tmp = baseBlock;
            baseBlock = baseSecondBlock;

            super.applyPowersToBlock();

            secondBlock = block;
            baseBlock = tmp;

            super.applyPowersToBlock();

            isSecondBlockModified = (secondBlock != baseSecondBlock);
        } else super.applyPowersToBlock();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
        secondBlock = baseSecondBlock;
        isSecondBlockModified = false;
        spinAmount = baseSpinAmount;
        isSpinAmountModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
        if (upgradedSecondBlock) {
            secondBlock = baseSecondBlock;
            isSecondBlockModified = true;
        }
        if (upgradedSpinAmount) {
            spinAmount = baseSpinAmount;
            isSpinAmountModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradedThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void upgradeSecondBlock(int amount) {
        baseSecondBlock += amount;
        secondBlock = baseSpinAmount;
        upgradedSecondBlock = true;
    }

    protected void upgradeSpinAmount(int amount) {
        baseSpinAmount -= amount;
        spinAmount = baseSpinAmount;
        upgradedSpinAmount = true;
    }

    protected void uDesc() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public abstract void upp();

    public void attune(int times) {
        extraAttunings += times;
        AttunedPatches.attune(this, times);
    };

    public void activate() {};

    public AbstractGameAction partActivation() {
        if (part) {
            AbstractCard c = this;
            return new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    int timesToActivate = 1;
                    if (AbstractDungeon.player.hasPower(Efficiency.EfficiencyPower.POWER_ID)) {
                        AbstractDungeon.player.getPower(Efficiency.EfficiencyPower.POWER_ID).flash();
                        timesToActivate += pwrAmt(AbstractDungeon.player, Efficiency.EfficiencyPower.POWER_ID);
                    }
                    for (int i = 0; i < timesToActivate; i++)
                        att(new AbstractGameAction() {
                            public void update() {
                                isDone = true;
                                flash(Color.WHITE.cpy());
                                activate();
                                AttunedPatches.CountPlay.count(c, false);
                            }
                        });
                }
            };
        }
        return null;
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        AbstractGameAction partAction = partActivation();
        if (partAction != null)
            atb(partAction);
    };

    public void update() {
        super.update();
        if (needsArtRefresh) {
            CardArtRoller.computeCard(this);
        }
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }
    
    protected void randomDmg(AbstractGameAction.AttackEffect fx) {
        atb(new AttackDamageRandomEnemyAction(this, fx));
    }
    
    protected void randomDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new AttackDamageRandomEnemyAction(this, fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, baseDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, baseDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void blckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void altBlck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, secondBlock));
    }

    protected void altBlckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, secondBlock));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }
}