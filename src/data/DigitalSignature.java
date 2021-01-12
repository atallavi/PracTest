package data;

import java.util.Arrays;

public class DigitalSignature {

    private final byte[] digitalSignature;

    public DigitalSignature(byte[] digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public byte[] getDigitalSignature() {
        return digitalSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature ds = (DigitalSignature) o;
        return Arrays.equals(digitalSignature, ds.digitalSignature);
    }

    @Override
    public int hashCode() { return Arrays.hashCode(digitalSignature); }

    @Override
    public String toString() {
        String ds = new String( digitalSignature );
        return "DigitalSignature{" + "personal signature='" + ds + '\'' + '}';
    }
}
