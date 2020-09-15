package com.rumaruka.thaumicbases.client.render;

import javax.vecmath.Matrix4f;
import com.rumaruka.thaumicbases.init.TBItems;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.util.vector.Vector3f;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.item.ItemStack;

public class HerobrinesScytheMH  {

    private ItemStack tool;



    private static ItemTransformVec3f thirdPersonLeft = null;
    private static ItemTransformVec3f thirdPersonRight = null;
    private static ItemTransformVec3f firstPersonLeft = null;
    private static ItemTransformVec3f firstPersonRight = null;
    public ItemCameraTransforms getItemCameraTransforms() {

        Vector3f rotation = new Vector3f();
        Vector3f translation = new Vector3f();
        Vector3f scale = new Vector3f(1f, 1f, 1f);

        // Third Person
        rotation = new Vector3f(0,4.8F,2.2F); // (0, 90, -35)
        translation = new Vector3f(0,0,-0.1F);
        if (tool != null && tool.getItem() == TBItems.herobrinesscythe) {
            translation.y += 2.0f;
        }
        translation.scale(0.0625f);
        scale = new Vector3f(2,2,1);
        thirdPersonRight = new ItemTransformVec3f(rotation, translation, scale);

        //rotation = new Vector3f(0f, (float) Math.PI / 2f, (float) -Math.PI * 7f / 36f); // (0, 90, -35)
      //  thirdPersonLeft = new ItemTransformVec3f(rotation, translation, scale);

        // First Person
        rotation = new Vector3f(0,0.8F,0);
        translation = new Vector3f(0,0.4F,0);
        if (tool != null && tool.getItem() == TBItems.herobrinesscythe) {
            translation.y += 1.5f;
        }
        translation.scale(0.0625f);
        scale = new Vector3f(0,0.4F,0);
        firstPersonRight = new ItemTransformVec3f(rotation, translation, scale);

        //rotation = new Vector3f(0f, (float) Math.PI * 1f / 2f, (float) -Math.PI * 5f / 36f);
       // firstPersonLeft = new ItemTransformVec3f(rotation, translation, scale);

        // Head and GUI are default.
        return new ItemCameraTransforms(thirdPersonLeft, thirdPersonRight, firstPersonLeft,
                firstPersonRight, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT,
                ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
    }
    public static Matrix4f getMatrix(ItemTransformVec3f transform) {

        javax.vecmath.Matrix4f m = new javax.vecmath.Matrix4f(), t = new javax.vecmath.Matrix4f();
        m.setIdentity();
        m.setTranslation(TRSRTransformation.toVecmath(transform.translation));
        t.setIdentity();
        t.rotY(transform.rotation.y);
        m.mul(t);
        t.setIdentity();
        t.rotX(transform.rotation.x);
        m.mul(t);
        t.setIdentity();
        t.rotZ(transform.rotation.z);
        m.mul(t);
        t.setIdentity();
        t.m00 = transform.scale.x;
        t.m11 = transform.scale.y;
        t.m22 = transform.scale.z;
        m.mul(t);
        return m;
    }
}
