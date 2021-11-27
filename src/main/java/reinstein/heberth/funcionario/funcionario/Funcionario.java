package reinstein.heberth.funcionario.funcionario;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Funcionario")
@Table(
        name = "funcionario",
        uniqueConstraints = {
                @UniqueConstraint(name = "funcionario_email_unique", columnNames = "email")
        }
)
public class Funcionario {
    @Id
    @SequenceGenerator(
            name = "funcionario_sequence",
            sequenceName = "funcionario_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    private String nome;
    private String sobrenome;
    private String email;
    private Long pis;

    public Funcionario(String nome, String sobrenome, String email, Long pis) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.pis = pis;
    }

    public Funcionario() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPis() {
        return pis;
    }

    public void setPis(Long pis) {
        this.pis = pis;
    }
}
