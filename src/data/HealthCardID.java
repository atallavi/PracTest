package data;

import exceptions.HealthCardException;

final public class HealthCardID  {
    private final String personalID;

    public HealthCardID(String code) throws HealthCardException {
        if (code==null) throw new HealthCardException("Health Card can not be null");
        if (!isValid( code )) throw new HealthCardException("Format expected");
        this.personalID = code;
    }

    private boolean isValid(String code){
        return code.matches( "[B]{6}+[A-Z]{2}+[0-9]{18}" );
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    @Override
    public int hashCode () { return personalID.hashCode(); }

    @Override
    public String toString () {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }
}
