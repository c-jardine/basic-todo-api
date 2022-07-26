package com.keplux.todo_app.subtask;

import com.keplux.todo_app.task.Task;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subtask")
@AllArgsConstructor
@NoArgsConstructor
public class Subtask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  @Column(name = "id")
  private Long id;

  @Getter
  @Setter
  @Column(name = "title")
  private String title;

  @Getter
  @Setter
  @Column(name = "is_complete")
  private Boolean isComplete;

  @Getter
  @Setter
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "task_id", nullable = false)
//  @OnDelete(action = OnDeleteAction.CASCADE)
  private Task task;
}
