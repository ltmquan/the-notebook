package notebook.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import notebook.backend.constants.EntitySize;

@Entity
@Table(name="notebook")
@Data
@NoArgsConstructor
public class Notebook {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", length=EntitySize.NOTEBOOK_NAME)
	private String name;
	
	@Column(name="description", length=EntitySize.NOTEBOOK_DESCRIPTION)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
}
