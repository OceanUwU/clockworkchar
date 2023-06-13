package clockworkchar.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static clockworkchar.ClockworkChar.makeID;
import static clockworkchar.util.Wiz.*;

import clockworkchar.characters.TheClockwork;
import clockworkchar.powers.AbstractEasyPower;

public class UnleashRage extends AbstractEasyCard {
    public final static String ID = makeID("UnleashRage");

    public UnleashRage() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 2;
        part = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void activate() {
        applyToSelfTop(new Boiling(adp(), magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }

    public static class Boiling extends AbstractEasyPower {
        public static String POWER_ID = makeID("Boiling");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public Boiling(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void atStartOfTurn() {
            flash();
            applyToEnemyTop(owner, new LoseStrengthPower(owner, amount));
            applyToEnemyTop(owner, new StrengthPower(owner, amount));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            if (adp() instanceof TheClockwork)
                vfxTop(new BoilEffect());
        }

        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public static class BoilEffect extends AbstractGameEffect {
            private static final float DUR = 1.2f;
            private static final int PARTICLES = 30;

            private int emitted = 0;

            public BoilEffect() {
                duration = DUR;
            }

            public void update() {
                if (duration == DUR)
                    CardCrawlGame.sound.play(makeID("BOIL"));
                duration -= Gdx.graphics.getDeltaTime();
                float shouldveEmitted = (1 - duration / DUR) * (float)PARTICLES;
                while (emitted < shouldveEmitted)
                    AbstractDungeon.effectsQueue.add(new SteamEffect(emitted++ % 2 == 0));
                if (emitted >= PARTICLES)
                    isDone = true;
            }

            public void render(SpriteBatch sb) {}
            public void dispose() {}

            public static class SteamEffect extends AbstractGameEffect {
                private static final float DUR = 1.9f;
                private static final float XOFFSET = 10f;
                private static final float XGAP = 30f;
                private static final float YOFFSET = 50f;
                private static final float xV = 80f;
                private static final float yV = 70f;
                private static final float SCALEMULT = 1.8f;
                private float x, y, dir;
                private float rotationSpeed;
                private TextureAtlas.AtlasRegion img;

                public SteamEffect(boolean right) {
                    duration = DUR;
                    float tint = MathUtils.random(0.8f, 0.9f);
                    color = new Color(tint, tint, tint + 0.05f, 1f);
                    img = MathUtils.randomBoolean() ? ImageMaster.EXHAUST_L : ImageMaster.EXHAUST_S;
                    dir = right ? 1f : -1f;
                    x = ((TheClockwork)adp()).getSkeleton().getX() + ((TheClockwork)adp()).getSkeleton().findBone("head").getWorldX() + XOFFSET * Settings.scale + XGAP * Settings.scale * dir;
                    y = ((TheClockwork)adp()).getSkeleton().getY() + ((TheClockwork)adp()).getSkeleton().findBone("head").getWorldY() + YOFFSET * Settings.scale;
                    rotation = MathUtils.random(360f);
                    rotationSpeed = MathUtils.random(-80f, 80f);
                    scale = 0f;
                }

                public void update() {
                    duration -= Gdx.graphics.getDeltaTime();
                    if (duration <= 0f) {
                        isDone = true;
                        return;
                    }
                    x += xV * Settings.scale * Gdx.graphics.getDeltaTime() * dir * (duration / DUR);
                    y += yV * Settings.scale * Gdx.graphics.getDeltaTime();
                    rotation += rotationSpeed * Gdx.graphics.getDeltaTime();
                    scale = Settings.scale * SCALEMULT * (1 - duration / DUR);
                    color.a = duration / DUR;
                }

                public void render(SpriteBatch sb) {
                    sb.setColor(color);
                    sb.draw(img, x - img.packedWidth / 2f, y - img.packedHeight / 2f, img.packedWidth / 2f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale, scale, rotation);
                }

                public void dispose() {}
            }
        }
    }
}