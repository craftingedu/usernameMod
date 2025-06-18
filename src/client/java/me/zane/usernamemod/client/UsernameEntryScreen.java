package me.zane.usernamemod.client;

import com.mojang.authlib.GameProfile;
import me.zane.usernamemod.UsernameStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UsernameEntryScreen extends Screen {

    private static final Identifier BACKGROUND_TEXTURE = Identifier.of("minecraft", "textures/gui/options_background.png");

    private final Screen parent;
    private TextFieldWidget usernameField;

    public UsernameEntryScreen(Screen parent) {
        super(Text.of("Enter Username"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Username input field
        usernameField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 20, 200, 20, Text.of("Username"));
        usernameField.setText(UsernameStorage.username);
        this.addSelectableChild(usernameField);
        this.setInitialFocus(usernameField);

        // Confirm button
        this.addDrawableChild(ButtonWidget.builder(Text.of("Confirm"), button -> {
            String newUsername = usernameField.getText().trim();
            if (!newUsername.isEmpty()) {
                UsernameStorage.username = newUsername;
                MinecraftClient.getInstance().setScreen(parent);
            }
        }).dimensions(centerX - 50, centerY + 10, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Blur background like Minecraft Options screen
        this.renderBackground(context, mouseX, mouseY, delta);

        // Draw buttons first
        super.render(context, mouseX, mouseY, delta);

        // Draw username input box LAST so it stays crisp
        usernameField.render(context, mouseX, mouseY, delta);

        // Draw title LAST so it matches buttons/textbox clarity
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // If Enter is pressed while the text field is focused
        if (this.usernameField.isFocused() && (keyCode == 257 || keyCode == 335)) {
            String newUsername = usernameField.getText().trim();
            if (!newUsername.isEmpty()) {
                UsernameStorage.username = newUsername;
                MinecraftClient.getInstance().setScreen(parent);
            }
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
