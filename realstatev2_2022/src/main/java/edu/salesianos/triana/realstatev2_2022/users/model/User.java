package edu.salesianos.triana.realstatev2_2022.users.model;

import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.model.Interesa;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "user")
@Data


public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )

    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String nombre;

    private String apellidos;

    private String direccion;

    private String email;

    private String telefono;

    private String avatar;

    private String password;

    @Enumerated(EnumType.STRING)
    private UsersRoles role;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda")
    private List<Interesa> listInteresa = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createDateAuthority;

    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id", foreignKey = @ForeignKey(name = "FK_USER_INMOBILIARIA"))
    private Inmobiliaria inmobiliaria;


    @Builder.Default
    @OneToMany(mappedBy = "interesado",fetch = FetchType.EAGER)
    private List<Interesa> interesa = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "propietario", cascade = CascadeType.ALL)
    private List<Vivienda> viviendas = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    public void addInmobiliaria(Inmobiliaria inmobili) {
        this.inmobiliaria = inmobili;
        inmobili.getGestores().add(this);
    }

    public void removeInmobiliaria(Inmobiliaria inmobili) {
        inmobili.getGestores().remove(this);
        this.inmobiliaria = null;
    }

}
