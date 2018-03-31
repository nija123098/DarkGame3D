package um.nija123098.guis;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import um.nija123098.model.RawModel;
import um.nija123098.render.Loader;
import um.nija123098.toolbox.Maths;

import java.util.List;

/**
 * Created by Jack on 7/17/2016.
 */
public class GuiRenderer {

    private final RawModel quad;
    private GuiShader guiShader;

    public GuiRenderer(Loader loader){
        this.quad = loader.loadToVAO(new float[]{-1, 1, -1, -1, 1, 1, 1, -1},2);
        this.guiShader = new GuiShader();
    }

    public void render(List<GuiTexture> guis){
        this.guiShader.start();
        GL30.glBindVertexArray(this.quad.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for (GuiTexture gui : guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix4f = Maths.makeTransformationMatrix(gui.getPosition(), gui.getScail());
            this.guiShader.loadTransformation(matrix4f);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        this.guiShader.stop();
    }
    public void cleanUp(){
        this.guiShader.cleanUp();
    }

}
