package com.kerust.tankwar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TankFrameMenuBar {

    private Tank tank = null;
    private TankWarFrame tankWarFrame = null;

    public TankFrameMenuBar(Tank tank, TankWarFrame tankWarFrame) {
        this.tank = tank;
        this.tankWarFrame = tankWarFrame;
    }

    public MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Settings");
        MenuItem menuItemGodOn = new MenuItem("God On");
        MenuItem menuItemRestart = new MenuItem("Restart");
        MenuItem menuItemSound = new MenuItem("Enable Sound");

        menuItemRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tank.setLiving(true);
                tank.setLifeNumber(3);
            }
        });

        menuItemGodOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemGodOn.setLabel(tankWarFrame.getGodOn() ? "God On" : "God Off");
                tankWarFrame.setGodOn(! tankWarFrame.getGodOn());
            }
        });

        menuItemSound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemSound.setLabel(tankWarFrame.enableSound() ? "Sound On" : "Sound Off");
                tankWarFrame.enableSound(! tankWarFrame.enableSound());
            }
        });
        menu.add(menuItemRestart);
        menu.add(menuItemGodOn);
        menu.add(menuItemSound);
        menuBar.add(menu);

        return menuBar;
    }

}
