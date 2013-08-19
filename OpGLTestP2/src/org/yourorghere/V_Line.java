/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yourorghere;

/**
 *
 * @author Law
 */
public class V_Line {
    V_Point v1,v2;
    double a,b,c;
    double Y_min,Y_max;
    boolean isFlat=false;
    public V_Line(V_Point v1,V_Point v2) {

        if(v1.y>v2.y)
        {
            Y_max=v1.y;
            Y_min=v2.y;
            this.v2=v1;
            this.v1=v2;
        }
        else if(v1.y<v2.y)
        {
            Y_max=v2.y;
            Y_min=v1.y;
            this.v1=v1;
            this.v2=v2;
        }
        else{
            isFlat=true;
            this.v1=v1;
            this.v2=v2;
        }
        a=v1.y-v2.y;
        b=v2.x-v1.x;
        c=v1.x*v2.y-v1.y*v2.x;
    }


}
