package clockworkchar.cards;

import clockworkchar.CrankyMod;
import clockworkchar.actions.AttuneAction;
import clockworkchar.util.TexLoader;
import clockworkchar.vfx.AttuneCardEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

public class Toolerang extends AbstractCrankyCard {
    public final static String ID = makeID("Toolerang");

    public Toolerang() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        vfx(new ToolerangEffect(p, m));
        atb(new WaitAction(ToolerangEffect.DURATION / 2f));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new VFXAction(p, new AttuneCardEffect(this, true), 0.0f, true));
        atb(new AttuneAction(this, magicNumber));
        atb(new WaitAction(ToolerangEffect.DURATION / 2f));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class ToolerangEffect extends AbstractGameEffect {
        public static float DURATION = 1.4f;
        private static TextureRegion TOOL_TEXTURE = new TextureRegion(TexLoader.getTexture(CrankyMod.makeImagePath("vfx/throwabletool.png")));
        private static float W = TOOL_TEXTURE.getTexture().getWidth() / 2;
        private static float H = TOOL_TEXTURE.getTexture().getWidth() / 2;
        private static float SPIN_SPEED = 300f;

        private float progress = 0f;
        private float sX, sY, tX, tY, x, y;
        private float angle = 0f;

        public ToolerangEffect(AbstractCreature source, AbstractCreature target) {
            duration = 0f;
            sX = source.hb.cX;
            sY = source.hb.cY;
            tX = target.hb.cX;
            tY = target.hb.cY;
        }

        public void update() {
            duration += Gdx.graphics.getDeltaTime();
            isDone = duration > DURATION;
            progress = (float)Math.sin((duration / DURATION) * (float)Math.PI);
            angle = duration * SPIN_SPEED;
            x = sX + (tX - sX) * progress;
            y = sY + (tY - sY) * progress;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(TOOL_TEXTURE, x - W * Settings.scale, y - W * Settings.scale, W, H, W*2, H*2, progress, progress, angle);
        }

        public void dispose() {}
    }
}