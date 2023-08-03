package clockworkchar.cards;

import clockworkchar.powers.AbstractEasyPower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import spireTogether.SpireTogetherMod;
import spireTogether.patches.network.CreatureSyncPatches;

import static clockworkchar.CrankyMod.makeID;
import static clockworkchar.util.Wiz.*;

import clockworkchar.multiplayer.ModManager;

public class ScatterNails extends AbstractCrankyCard {
    public final static String ID = makeID("ScatterNails");

    public ScatterNails() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 3;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction((AbstractCreature)p, new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.1F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        forAllMonstersLiving(q -> applyToEnemy(q, new NailsPower(q, magicNumber)));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    public static class NailsPower extends AbstractEasyPower implements HealthBarRenderPower {
        public static String POWER_ID = makeID("Nails");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        private static boolean sync;
    
        public NailsPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, amount);
        }
    
        public void atStartOfTurn() {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                flashWithoutSound();
                atb(new AbstractGameAction() {
                     public void update() {
                        isDone = true;
                        if (ModManager.isMultiplayerLoaded && SpireTogetherMod.isConnected) {
                            sync = CreatureSyncPatches.syncMonsterDamaged;
                            CreatureSyncPatches.syncMonsterDamaged = false;
                        }
                     }
                });
                atb(new LoseHPAction(this.owner, AbstractDungeon.player, this.amount, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                atb(new AbstractGameAction() {
                     public void update() {
                        isDone = true;
                        if (ModManager.isMultiplayerLoaded && SpireTogetherMod.isConnected)
                            CreatureSyncPatches.syncMonsterDamaged = sync;
                     }
                });
            }
        }
        
        public int getHealthBarAmount() {
            return this.amount;
        }
    
        public Color getColor() {
            return Color.LIGHT_GRAY;
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
    }
}