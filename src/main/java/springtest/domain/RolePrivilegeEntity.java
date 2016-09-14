package springtest.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by ligson on 2016/8/31.
 */
@Entity
@Table(name = "u_rolePrivilege")
public class RolePrivilegeEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToOne(targetEntity = RoleEntity.class)
    private RoleEntity role;

    @ManyToOne(targetEntity = PrivilegeEntity.class)
    private PrivilegeEntity privilege;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public PrivilegeEntity getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeEntity privilege) {
        this.privilege = privilege;
    }
}
