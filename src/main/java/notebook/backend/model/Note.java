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
@Table(name="note")
@Data
@NoArgsConstructor
public class Note {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", length=EntitySize.NOTE_NAME)
	private String name;
	
	@Column(name="content")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="type_id", referencedColumnName="id")
	private NoteType type;
	
	@ManyToOne
	@JoinColumn(name="notebook_id", referencedColumnName="id")
	private Notebook notebook;
	
}
