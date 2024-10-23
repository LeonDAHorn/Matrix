package org.cghorn.rendering;

import org.cghorn.objects.Object;
import org.cghorn.objects.Triangle;

public class Scene {

    Object[] objects;
    Triangle[] triangles = new Triangle[1000];

    public Scene(Object... objects){
        this.objects = objects;
        int index = 0;
        for(Object o : this.objects){
            for(Triangle t : o.triangles){
                triangles[index] = t;
                index++;
            }
        }
        Triangle[] tr = new Triangle[index];
        System.arraycopy(triangles, 0, tr, 0, index);
        triangles = tr;
    }

}
