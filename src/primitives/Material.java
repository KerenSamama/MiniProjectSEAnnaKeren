package primitives;

public class Material {

    public double Kd =0; // diffuse coefficient
    public double Ks =0; // specular coefficient
    public int nShininess=0; // the object’s shininess


    /**
     * Update method for Kd
     * @param kd diffuse coefficient
     * @return the Material object itself for chaining calls
     */
    public Material setKd(double kd) {
        this.Kd = kd;
        return this;
    }

    /**
     * Update method for Ks
     * @param ks specular coefficient
     * @return the Material object itself for chaining calls
     */
    public Material setKs(double ks) {
        this.Ks = ks;
        return this;
    }

    /**
     * Update method for nShininess
     * @param nShininess the object’s shininess
     * @return the Material object itself for chaining calls
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
