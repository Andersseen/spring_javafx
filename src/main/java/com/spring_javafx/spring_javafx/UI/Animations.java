package com.spring_javafx.spring_javafx.UI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class Animations {
    private InnerShadow setInnerShadow(float number){
        InnerShadow is = new InnerShadow();
        is.setOffsetX(number);
        is.setOffsetY(number);
        is.setColor(Color.BLACK);
        return is;
    }
    private DropShadow setDropShadow(float number){
        DropShadow ds = new DropShadow();
        ds.setOffsetX(number);
        ds.setOffsetY(number);
        ds.setColor(Color.BLACK);
        return ds;
    }

    public Timeline getAnimationInnerShadow(Node element){
       return new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(element.effectProperty(), this.setInnerShadow(.1f))),
                new KeyFrame(Duration.seconds(0), new KeyValue(element.opacityProperty(), .2)),
                new KeyFrame(Duration.seconds(.1), new KeyValue(element.effectProperty(), this.setInnerShadow(2.0f))),
                new KeyFrame(Duration.seconds(.2), new KeyValue(element.effectProperty(), this.setInnerShadow(0f))),
                new KeyFrame(Duration.seconds(.2), new KeyValue(element.opacityProperty(), 1)));
    }
    private Timeline getAnimationDropShadow(Node element){
       return new Timeline(
               new KeyFrame(Duration.seconds(0), new KeyValue(element.effectProperty(), this.setDropShadow(1.0f))),
               new KeyFrame(Duration.seconds(0), new KeyValue(element.opacityProperty(), .5)),
               new KeyFrame(Duration.seconds(.1), new KeyValue(element.effectProperty(), this.setDropShadow(1.5f))),
               new KeyFrame(Duration.seconds(.2), new KeyValue(element.effectProperty(), this.setDropShadow(2.0f))),
               new KeyFrame(Duration.seconds(.2), new KeyValue(element.opacityProperty(), 1)));
    }

    public void btnHoverEffects(Node element){
        element.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               getAnimationInnerShadow(element).play();
            }
        });
        element.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getAnimationDropShadow(element).play();
            }
        });
        element.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                element.setEffect(null);
            }
        });

    }
}
