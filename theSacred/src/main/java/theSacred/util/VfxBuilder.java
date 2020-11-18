package theSacred.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;


//Credits to NotInTheFace ( https://github.com/arasmus8/ChefMod/blob/master/src/main/java/chefmod/vfx/VfxBuilder.java )
/**
 * <p>
 * Build your own visual effect using declarative primitives! This will quickly and easily build simple visual effects
 * without getting too bogged down in the math.
 * </p>
 * <p>
 * How to use:
 * You can chain methods together to describe the desired behavior of the visual effect, then get the AbstractGameEffect
 * instance using the build() method.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <p>
 * 1 - Shoot a growing star at an enemy. It begins from the player, lasts 0.8 seconds, grows from 80% to 220% in size,
 * has a yellow color, gets faster as it travels, and rotates clockwise.
 * </p>
 * <pre>
 * AbstractGameEffect shootStar = new VfxBuilder(ImageMaster.TINY_STAR, p.drawX, m.hb.cY, 0.8f)
 * .scale(0.8f, 2.2f, VfxBuilder.Interpolations.SWING)
 * .setColor(new Color(0xFFFD9CFF))
 * .xRange(p.drawX, m.drawX, VfxBuilder.Interpolations.EXP5IN)
 * .rotate(-400f)
 * .build()
 * </pre>
 * <p>
 * 2 - Drop a rock from above. It bounces, and fades in and out. It also plays a sound partway through.
 * </p>
 * <pre>
 *
 * AbstractGameEffect rockEffect = new VfxBuilder(rockTexture, m.hb.cX, 0f, 2f)
 * .yRange(Settings.HEIGHT, m.hb.y + m.hb.height / 6, VfxBuilder.Interpolations.BOUNCE)
 * .fadeIn(0.25f)
 * .fadeOut(0.25f)
 * .setScale(1.25f * m.hb.width / t.getWidth())
 * .playSoundAt("BLUNT_HEAVY", 0.35f)
 * .build();
 * </pre>
 * <p>
 * 3 - A ball flies up and is pulled down offscreen.
 * </p>
 * <pre>
 * AbstractGameEffect ballEffect = new VfxBuilder(ballTexture, 1.5f)
 * .velocity(MathUtils.random(45f, 135f), 300f)
 * .gravity(1000f)
 * .build();
 * </pre>
 */
public class VfxBuilder {
    private float duration;
    private float scale = 1f;
    private float angle = 0f;
    private float alpha = 1f;
    private float x;
    private float y;
    private final AtlasRegion img;
    private Color color = Color.WHITE.cpy();
    private List<Predicate<Float>> updaters;
    private final Queue<List<Predicate<Float>>> animStages;
    private final Queue<Float> durationList;
    private boolean additive = false;

    /**
     * Build a visual effect using an AtlasRegion
     *
     * @param img      the image to draw
     * @param x        the default x position
     * @param y        the default y position
     * @param duration the duration of the effect, in seconds
     */
    public VfxBuilder(AtlasRegion img, float x, float y, float duration) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.duration = duration;
        updaters = new ArrayList<>();
        animStages = new LinkedList<>();
        durationList = new LinkedList<>();
    }

    /**
     * Build a visual effect using an AtlasRegion
     *
     * @param img      the image to draw
     * @param duration the duration of the effect, in seconds
     */
    public VfxBuilder(AtlasRegion img, float duration) {
        this(img, 0f, 0f, duration);
    }

    /**
     * Build a visual effect without an image. Can be used to emit particles or trigger other effects.
     */
    public VfxBuilder(float duration) {
        this((AtlasRegion) null, 0f, 0f, duration);
    }

    /**
     * Build a visual effect using a Texture
     *
     * @param texture  the image to draw
     * @param x        the default x position
     * @param y        the default y position
     * @param duration the duration of the effect, in seconds
     */
    public VfxBuilder(Texture texture, float x, float y, float duration) {
        this(
                new AtlasRegion(
                        texture,
                        0,
                        0,
                        texture.getWidth(),
                        texture.getHeight()
                ),
                x,
                y,
                duration
        );
    }

    /**
     * Build a visual effect using a Texture
     *
     * @param texture  the image to draw
     * @param duration the duration of the effect, in seconds
     */
    public VfxBuilder(Texture texture, float duration) {
        this(texture, 0f, 0f, duration);
    }

    /**
     * Use additive blending - colors are overlayed onto the underlying pixels.
     *
     * @return this builder
     */
    public VfxBuilder useAdditiveBlending() {
        additive = true;
        return this;
    }

    private Function<Float, Float> interpolator(float from, float to, Interpolations interpolation) {
        switch (interpolation) {
            case BOUNCE:
                return t -> Interpolation.bounceOut.apply(from, to, t);
            case BOUNCEIN:
                return t -> Interpolation.bounceIn.apply(from, to, t);
            case CIRCLE:
                return t -> Interpolation.circleOut.apply(from, to, t);
            case CIRCLEIN:
                return t -> Interpolation.circleIn.apply(from, to, t);
            case ELASTIC:
                return t -> Interpolation.elasticOut.apply(from, to, t);
            case ELASTICIN:
                return t -> Interpolation.elasticIn.apply(from, to, t);
            case EXP5:
                return t -> Interpolation.exp5Out.apply(from, to, t);
            case EXP5IN:
                return t -> Interpolation.exp5In.apply(from, to, t);
            case EXP10:
                return t -> Interpolation.exp10Out.apply(from, to, t);
            case EXP10IN:
                return t -> Interpolation.exp10In.apply(from, to, t);
            case SMOOTH:
                return t -> Interpolation.smooth.apply(from, to, t);
            case SWING:
                return t -> Interpolation.swingOut.apply(from, to, t);
            case SWINGIN:
                return t -> Interpolation.swingIn.apply(from, to, t);
            case LINEAR: // fallthrough
            default:
                return t -> Interpolation.linear.apply(from, to, t);
        }
    }

    /**
     * Set the X coordinate of the image to a value. This value will be overridden by any of the functions that animate
     * the X position.
     *
     * @param value the x coordinate to set
     * @return this builder
     */
    public VfxBuilder setX(float value) {
        updaters.add(t -> {
            x = value;
            return true;
        });
        return this;
    }

    /**
     * Set the Y coordinate of the image to a value. This value will be overridden by any of the functions that animate
     * the Y position.
     *
     * @param value the y coordinate to set
     * @return this builder
     */
    public VfxBuilder setY(float value) {
        updaters.add(t -> {
            y = value;
            return true;
        });
        return this;
    }

    /**
     * Move the image along the x-axis over the duration. Incompatible with the arc, velocity, and gravity functions.
     *
     * @param from          start x position
     * @param to            end x position
     * @param interpolation the interpolation to use
     * @return this builder
     */
    public VfxBuilder moveX(float from, float to, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(from, to, interpolation);
        updaters.add(t -> {
            x = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    /**
     * Move the image along the x-axis over the duration. Incompatible with the arc, velocity, and gravity functions.
     *
     * @param from start x position
     * @param to   end x position
     * @return this builder
     */
    public VfxBuilder moveX(float from, float to) {
        return moveX(from, to, Interpolations.EXP5);
    }

    /**
     * Oscillate the image along the x-axis throughout the duration.
     *
     * @param min   Minimum x value
     * @param max   Maximum x value
     * @param speed How fast to oscillate, in degrees per second.
     * @return this builder
     */
    public VfxBuilder oscillateX(float min, float max, float speed) {
        updaters.add(t -> {
            x = min + (max - min) * (MathUtils.sin(MathUtils.degreesToRadians * t * speed) / 2f + 0.5f);
            return false;
        });
        return this;
    }

    /**
     * Move the image along the y-axis over the duration. Incompatible with the arc, velocity, and gravity functions.
     *
     * @param from          start y position
     * @param to            end y position
     * @param interpolation the interpolation scheme to use
     * @return this builder
     */
    public VfxBuilder moveY(float from, float to, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(from, to, interpolation);
        updaters.add(t -> {
            y = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    /**
     * Move the image along the y-axis over the duration. Incompatible with the arc, velocity, and gravity functions.
     *
     * @param from start y position
     * @param to   end y position
     * @return this builder
     */
    public VfxBuilder moveY(float from, float to) {
        return moveY(from, to, Interpolations.EXP5);
    }

    /**
     * Oscillate the image along the y-axis throughout the duration.
     *
     * @param min   Minimum y value
     * @param max   Maximum y value
     * @param speed How fast to oscillate, in degrees per second.
     * @return this builder
     */
    public VfxBuilder oscillateY(float min, float max, float speed) {
        updaters.add(t -> {
            y = min + (max - min) * (MathUtils.sin(MathUtils.degreesToRadians * t * speed) / 2f + 0.5f);
            return false;
        });
        return this;
    }

    /**
     * Arc the image from the start position to the end position over the duration. Incompatible
     * with the xRange, yRange, velocity, and gravity functions.
     *
     * @param fromX     start x position
     * @param fromY     start y position
     * @param toX       end x position
     * @param toY       end y position
     * @param maxHeight the y position at the peak of the arc
     * @return this builder
     */
    public VfxBuilder arc(float fromX, float fromY, float toX, float toY, float maxHeight) {
        Function<Float, Float> upFn = interpolator(fromY, maxHeight, Interpolations.CIRCLE);
        Function<Float, Float> downFn = interpolator(toY, maxHeight, Interpolations.CIRCLE);
        Function<Float, Float> xFn = interpolator(fromX, toX, Interpolations.LINEAR);
        updaters.add(t -> {
            x = xFn.apply(t / duration);
            return false;
        });
        updaters.add(t -> {
            float halfDuration = duration / 2f;
            if (t > halfDuration) {
                return true;
            }
            y = upFn.apply(t / halfDuration);
            return false;
        });
        updaters.add(t -> {
            float halfDuration = duration / 2f;
            if (t > halfDuration) {
                float a = MathUtils.clamp(duration - t, 0, 1);
                y = downFn.apply(a / halfDuration);
            }
            return false;
        });
        return this;
    }

    /**
     * Pull the image along the y-axis with stronger force over the duration. Not compatible with xRange, yRange,
     * and arc functions.
     *
     * @param strength the force to apply. negative values will pull the image up
     * @return this builder
     */
    public VfxBuilder gravity(float strength) {
        updaters.add(t -> {
            y -= Settings.scale * strength * t / duration;
            return false;
        });
        return this;
    }

    /**
     * Propel the image with a constant velocity. Incompatible with xRange, yRange, and arc functions.
     *
     * @param angle the angle from the original position, in degrees
     * @param speed the speed of the image, in world units per second
     * @return this builder
     */
    public VfxBuilder velocity(float angle, float speed) {
        float rads = MathUtils.degreesToRadians * angle;
        float scaledSpeed = speed * Settings.scale * Gdx.graphics.getDeltaTime();
        float dx = MathUtils.cos(rads) * scaledSpeed;
        float dy = MathUtils.sin(rads) * scaledSpeed;
        updaters.add(t -> {
            x += dx;
            y += dy;
            return false;
        });
        return this;
    }

    /**
     * Set the draw color for the image.
     *
     * @param value the color to render with
     * @return this builder
     */
    public VfxBuilder setColor(Color value) {
        color = value.cpy();
        return this;
    }

    /**
     * Set the opacity of the image to a constant value.
     * This will be overriden if you use the fadeIn or fadeOut functions.
     *
     * @param value the opacity level (between 0 and 1)
     * @return this builder
     */
    public VfxBuilder setAlpha(float value) {
        alpha = value;
        return this;
    }

    /**
     * Fade the image in from transparent at the start of the effect duration.
     * This will override the setAlpha function.
     *
     * @param fadeTime the time offset when the image will be fully opaque
     * @return this builder
     */
    public VfxBuilder fadeIn(float fadeTime) {
        updaters.add(t -> {
            alpha = Interpolation.fade.apply(t / fadeTime);
            return t > fadeTime;
        });
        return this;
    }

    /**
     * Fade out the image to transparent at the end of the effect duration.
     * This will override the setAlpha function.
     *
     * @param fadeTime the time to start fading the image
     * @return this builder
     */
    public VfxBuilder fadeOut(float fadeTime) {
        updaters.add(t -> {
            float a = MathUtils.clamp(duration - t, 0f, 1f);
            alpha = t > (duration - fadeTime) ? Interpolation.fade.apply(a / fadeTime) : 1f;
            return false;
        });
        return this;
    }

    /**
     * Oscillate the transparency of the image throughout the duration.
     *
     * @param min   Minimum alpha value
     * @param max   Maximum alpha value
     * @param speed How fast to oscillate, in degrees per second.
     * @return this builder
     */
    public VfxBuilder oscillateAlpha(float min, float max, float speed) {
        updaters.add(t -> {
            alpha = min + (max - min) * (MathUtils.sin(MathUtils.degreesToRadians * t * speed) / 2f + 0.5f);
            return false;
        });
        return this;
    }

    /**
     * Set the image scale to a constant value (between 0 and 1).
     *
     * @param value scale to use
     * @return this builder
     */
    public VfxBuilder setScale(float value) {
        scale = value;
        return this;
    }

    /**
     * Scale the image over the duration of the effect, using the provided interpolation.
     * This will override the setScale function.
     *
     * @param from          starting scale (between 0 and 1)
     * @param to            final scale (between 0 and 1)
     * @param interpolation the interpolation to use
     * @return this builder
     */
    public VfxBuilder scale(float from, float to, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(from, to, interpolation);
        updaters.add(t -> {
            scale = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    /**
     * Scale the image over the duration of the effect, using the default Swing interpolation
     * This will override the setScale function.
     *
     * @param from starting scale (between 0 and 1)
     * @param to   final scale (between 0 and 1)
     * @return this builder
     */
    public VfxBuilder scale(float from, float to) {
        return scale(from, to, Interpolations.SWING);
    }

    /**
     * Oscillate the scale of the image throughout the duration.
     *
     * @param min   Minimum scale value
     * @param max   Maximum scale value
     * @param speed How fast to oscillate, in degrees per second.
     * @return this builder
     */
    public VfxBuilder oscillateScale(float min, float max, float speed) {
        updaters.add(t -> {
            scale = min + (max - min) * (MathUtils.sin(MathUtils.degreesToRadians * t * speed) / 2f + 0.5f);
            return false;
        });
        return this;
    }

    /**
     * Set the draw rotation angle of the image. This setting will be overridden by the rotateTo, rotate, and wobble
     * functions.
     *
     * @param value the angle of rotation, counter-clockwise, in degrees
     * @return this builder
     */
    public VfxBuilder setAngle(float value) {
        angle = value;
        return this;
    }

    /**
     * Rotate the image from one angle to another over the duration of the animation stage. Overrides setAngle.
     *
     * @param fromAngle     The angle at the start of the animation.
     * @param toAngle       The angle at the end of the animation.
     * @param interpolation The interpolation to use.
     * @return this builder
     */
    public VfxBuilder rotateTo(float fromAngle, float toAngle, Interpolations interpolation) {
        Function<Float, Float> fn = interpolator(fromAngle, toAngle, interpolation);
        updaters.add(t -> {
            angle = fn.apply(t / duration);
            return false;
        });
        return this;
    }

    /**
     * Rotate the image counter-clockwise. Not compatible with the wobble function. Overrides setAngle.
     *
     * @param speed the speed to rotate in degrees per second (negative values will rotate clockwise)
     * @return this builder
     */
    public VfxBuilder rotate(float speed) {
        updaters.add(t -> {
            angle += speed * Gdx.graphics.getDeltaTime();
            return false;
        });
        return this;
    }

    /**
     * Wobble the image between two specified angles. Not compatible with the rotate function. Overrides setAngle.
     *
     * @param startAngle the starting angle
     * @param otherAngle the other angle
     * @param speed      how quick to wobble in degrees per second
     * @return this builder
     */
    public VfxBuilder wobble(float startAngle, float otherAngle, float speed) {
        updaters.add(t -> {
            angle = startAngle + (otherAngle - startAngle) * (MathUtils.sin(MathUtils.degreesToRadians * t * speed) / 2f + 0.5f);
            return false;
        });
        return this;
    }

    /**
     * Trigger a sound effect at the specified delay, with the pitch adjusted
     *
     * @param timeOffset  The delay from the start of this effect to start playing the sound
     * @param pitchAdjust The factor to adjust the sound's pitch by
     * @param key         Find the list of valid strings in the SoundMaster class
     * @return this builder
     */
    public VfxBuilder playSoundAt(float timeOffset, float pitchAdjust, String key) {
        updaters.add(t -> {
            if (t >= timeOffset) {
                CardCrawlGame.sound.playA(key, pitchAdjust);
                return true;
            }
            return false;
        });
        return this;
    }

    /**
     * Trigger a sound effect at the specified delay
     *
     * @param timeOffset The delay from the start of this effect to start playing the sound
     * @param key        Find the list of valid strings in the SoundMaster class
     * @return this builder
     */
    public VfxBuilder playSoundAt(float timeOffset, String key) {
        updaters.add(t -> {
            if (t >= timeOffset) {
                CardCrawlGame.sound.play(key);
                return true;
            }
            return false;
        });
        return this;
    }

    /**
     * Trigger another effect at the specified delay - you can chain multiple effects together!
     *
     * @param timeOffset The delay from the start of this effect to trigger the chained effect.
     * @param count      The number of effects to generate.
     * @param effectFn   A function that will create the effect. It will be passed the current x and y coordinates as
     *                   parameters. For example, this call will generate an IceShatterEffect after a delay of 0.8 seconds:
     *                   <code>myBuilder.triggerVfxAt((x, y) -> new IceShatterEffect(x, y), 0.8f)</code>
     * @return this builder
     */
    public VfxBuilder triggerVfxAt(float timeOffset, int count, BiFunction<Float, Float, AbstractGameEffect> effectFn) {
        IntStream.rangeClosed(1, MathUtils.clamp(count, 1, 50))
                .forEachOrdered(i -> updaters.add(t -> {
                    if (t >= timeOffset) {
                        AbstractDungeon.effectsQueue.add(effectFn.apply(x, y));
                        return true;
                    }
                    return false;
                }));
        return this;
    }

    /**
     * Trigger another effect periodically - this acts something like a particle emitter.
     *
     * @param effectGeneratorFn A function that will create the effect. It will be passed the current x and y coordinates
     *                          as parameters. For example, this call will create a new instance of the CoolParticleEffect
     *                          every 0.25 seconds:
     *                          <code>myBuilder.emitEvery((x, y) -> new CoolParticleEffect(x, y), 0.25f)</code>
     * @param period            The periodic interval to wait between calls to the generator function.
     * @return this builder
     */
    public VfxBuilder emitEvery(BiFunction<Float, Float, AbstractGameEffect> effectGeneratorFn, float period) {
        IntStream.rangeClosed(1, (int) (duration / period))
                .forEachOrdered(offset -> updaters.add(t -> {
                    if (t >= period * offset) {
                        AbstractDungeon.effectsQueue.add(effectGeneratorFn.apply(x, y));
                        return true;
                    }
                    return false;
                }));
        return this;
    }

    /**
     * Start a new phase of the animation that starts from the end of the current phase.
     *
     * @param nextDuration The new duration to animate over.
     * @return this builder
     */
    public VfxBuilder andThen(float nextDuration) {
        animStages.add(updaters);
        durationList.add(duration);
        updaters = new ArrayList<>();
        duration = nextDuration;
        return this;
    }

    private boolean nextStage() {
        if (durationList.size() > 0) {
            duration = durationList.poll();
            updaters = animStages.poll();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Call this function to generate the AbstractGameEffect. You can run the effect with code like
     * addToBot(new VFXAction(yourEffect))
     * or
     * AbstractDungeon.effectsList.add(yourEffect)
     *
     * @return the effect you have built
     */
    public AbstractGameEffect build() {
        durationList.add(duration);
        animStages.add(updaters);
        nextStage();
        return new BuiltEffect(this);
    }

    private static class BuiltEffect extends AbstractGameEffect {
        private final VfxBuilder builder;
        private float t;

        public BuiltEffect(VfxBuilder builder) {
            this.builder = builder;
            t = 0;
            startingDuration = duration = builder.duration;
        }

        @Override
        public void update() {
            t += Gdx.graphics.getDeltaTime();
            builder.updaters.removeIf(fn -> fn.test(t));
            if (t >= duration) {
                isDone = builder.nextStage();
                startingDuration = duration = builder.duration;
                t = 0;
            }
        }

        @Override
        public void render(SpriteBatch sb) {
            if (builder.img == null) {
                return;
            }
            Color color = builder.color;
            color.a = builder.alpha;
            sb.setColor(color);
            float w = builder.img.packedWidth;
            float h = builder.img.packedHeight;
            float halfW = w / 2f;
            float halfH = h / 2f;
            if (builder.additive) {
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            }
            sb.draw(
                    builder.img,
                    builder.x - halfW,
                    builder.y - halfH,
                    halfW,
                    halfH,
                    w,
                    h,
                    builder.scale * Settings.scale,
                    builder.scale * Settings.scale,
                    builder.angle
            );
            if (builder.additive) {
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
        }

        @Override
        public void dispose() {
        }
    }

    /**
     * Interpolations recognized by the VFX Builder.
     * <p>
     * For more information, see: https://github.com/libgdx/libgdx/wiki/Interpolation
     */
    public enum Interpolations {
        BOUNCE,
        CIRCLE,
        ELASTIC,
        EXP5,
        EXP10,
        LINEAR,
        SMOOTH,
        SWING,
        BOUNCEIN,
        CIRCLEIN,
        ELASTICIN,
        EXP5IN,
        EXP10IN,
        SWINGIN
    }
}
