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
@Table(name="flashcard")
@Data
@NoArgsConstructor
public class Flashcard {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", length=EntitySize.FLASHCARD_TITLE)
	private String title;
	
	@Column(name="question")
	private String question;
	
	@Column(name="content")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="note_id", referencedColumnName="id")
	private Note note;
	
}
