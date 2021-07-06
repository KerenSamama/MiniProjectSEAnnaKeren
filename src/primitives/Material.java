package primitives;
/**
 * Material class is PDS with a basic Builder template.
 * All the fields are in public permission.
 */
public class Material {

    public double Kd = 0; // diffuse coefficient
    public double Ks = 0; // specular coefficient
    public int nShininess = 0; // the object’s shininess
    public double Kr = 0.0;//  reflective coefficient, opaque [0.0-1.0]
    public double Kt = 0.0;//  transparent coefficient, matt surface [0.0-1.0]



    /**
     * Update method for Kd
     *
     * @param kd diffuse coefficient
     * @return the Material object itself for chaining calls
     */
    public Material setKd(double kd) {
        this.Kd = kd;
        return this;
    }

    /**
     * Update method for Ks
     *
     * @param ks specular coefficient
     * @return the Material object itself for chaining calls
     */
    public Material setKs(double ks) {
        this.Ks = ks;
        return this;
    }

    /**
     * Update method for Kr
     *
     * @param kr reflective coefficient
     * @return the Material object itself for chaining calls
     */
    public Material setKr(double kr) {
        this.Kr = kr;
        return this;
    }

    /**
     * Update method for Kt
     *
     * @param kt transparent coefficient
     * @return the Material object itself for chaining calls
     */
    public Material setKt(double kt) {
        this.Kt = kt;
        return this;
    }

    /**
     * Update method for nShininess
     *
     * @param nShininess the object’s shininess
     * @return the Material object itself for chaining calls
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Getter function
     * @return Kt
     */
    public double getKt() {
        return Kt;
    }

    /**
     * Getter function
     * @return Ks
     */
    public double getKs() {
        return Ks;
    }
}
