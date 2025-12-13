    // Массив для хранения задач
    let tasks = [];

    // DOM элементы
    const newTaskInput = document.getElementById('new-task-input');
    const addTaskBtn = document.getElementById('add-task-btn');
    const tasksList = document.getElementById('tasks-list');
    const emptyState = document.getElementById('empty-state');
    const tasksCount = document.getElementById('tasks-count');
    const bulkActions = document.getElementById('bulk-actions');
    const selectedCount = document.getElementById('selected-count');
    const markSelectedCompleteBtn = document.getElementById('mark-selected-complete');
    const deleteSelectedBtn = document.getElementById('delete-selected');
    const clearSelectionBtn = document.getElementById('clear-selection');
    const selectAllBtn = document.getElementById('select-all-btn');

    // Загрузка задач из localStorage при загрузке страницы
    document.addEventListener('DOMContentLoaded', () => {
        const savedTasks = localStorage.getItem('tasks');
        if (savedTasks) {
            tasks = JSON.parse(savedTasks);
            renderTasks();
        }
        updateTasksCount();
    });

    // Функция для сохранения задач в localStorage
    function saveTasks() {
        localStorage.setItem('tasks', JSON.stringify(tasks));
    }

    // Функция для обновления счетчика задач
    function updateTasksCount() {
        const totalTasks = tasks.length;
        const completedTasks = tasks.filter(task => task.completed).length;
        tasksCount.textContent = `Задач: ${totalTasks} (Выполнено: ${completedTasks})`;

        // Показываем или скрываем состояние пустого списка
        if (totalTasks === 0) {
            emptyState.style.display = 'block';
        } else {
            emptyState.style.display = 'none';
        }
    }

    // Функция для обновления счетчика выбранных задач
    function updateSelectedCount() {
        const selectedTasks = tasks.filter(task => task.selected);
        selectedCount.textContent = `Выбрано: ${selectedTasks.length}`;

        // Показываем или скрываем блок массовых действий
        if (selectedTasks.length > 0) {
            bulkActions.classList.add('visible');
        } else {
            bulkActions.classList.remove('visible');
        }
    }

    // Функция для рендеринга списка задач
    function renderTasks() {
        tasksList.innerHTML = '';

        tasks.forEach((task, index) => {
            const taskItem = document.createElement('li');
            taskItem.className = `task-item ${task.completed ? 'completed' : ''}`;
            taskItem.dataset.index = index;

            taskItem.innerHTML = `
                <input type="checkbox" class="task-checkbox" ${task.selected ? 'checked' : ''}>
                <div class="task-content">${task.text}</div>
                <div class="task-actions">
                    <button class="task-btn complete-btn" title="${task.completed ? 'Возвратить' : 'Выполнить'}">
                        <i class="fas fa-${task.completed ? 'undo' : 'check'}"></i>
                    </button>
                    <button class="task-btn delete-btn" title="Удалить">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            `;

            tasksList.appendChild(taskItem);
        });

        // Добавляем обработчики событий для только что созданных элементов
        addTaskEventListeners();
        updateTasksCount();
        updateSelectedCount();
    }

    // Функция для добавления обработчиков событий к задачам
    function addTaskEventListeners() {
        // Обработчики для чекбоксов выбора задач
        document.querySelectorAll('.task-checkbox').forEach(checkbox => {
            checkbox.addEventListener('change', function() {
                const index = parseInt(this.closest('.task-item').dataset.index);
                tasks[index].selected = this.checked;
                updateSelectedCount();
                saveTasks();
            });
        });

        // Обработчики для кнопок выполнения задачи
        document.querySelectorAll('.complete-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.closest('.task-item').dataset.index);
                tasks[index].completed = !tasks[index].completed;
                renderTasks();
                saveTasks();
            });
        });

        // Обработчики для кнопок удаления задачи
        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.closest('.task-item').dataset.index);
                tasks.splice(index, 1);
                renderTasks();
                saveTasks();
            });
        });
    }

    // Добавление новой задачи
    addTaskBtn.addEventListener('click', () => {
        const taskText = newTaskInput.value.trim();

        if (taskText) {
            tasks.push({
                text: taskText,
                completed: false,
                selected: false
            });

            newTaskInput.value = '';
            renderTasks();
            saveTasks();
        }
    });

    // Добавление задачи при нажатии Enter
    newTaskInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            addTaskBtn.click();
        }
    });

    // Выделение всех задач
    selectAllBtn.addEventListener('click', () => {
        tasks.forEach(task => {
            task.selected = true;
        });
        renderTasks();
        saveTasks();
    });

    // Отметить выбранные задачи как выполненные
    markSelectedCompleteBtn.addEventListener('click', () => {
        tasks.forEach(task => {
            if (task.selected) {
                task.completed = true;
            }
        });
        renderTasks();
        saveTasks();
    });

    // Удалить выбранные задачи
    deleteSelectedBtn.addEventListener('click', () => {
        if (confirm('Вы уверены, что хотите удалить выбранные задачи?')) {
            tasks = tasks.filter(task => !task.selected);
            renderTasks();
            saveTasks();
        }
    });

    // Очистить выбор всех задач
    clearSelectionBtn.addEventListener('click', () => {
        tasks.forEach(task => {
            task.selected = false;
        });
        renderTasks();
        saveTasks();
    });

    // Инициализация с примером задач
    function initializeExampleTasks() {
        if (tasks.length === 0) {
            tasks = [
                { text: 'Изучить JavaScript', completed: true, selected: false },
                { text: 'Создать ToDo List приложение', completed: true, selected: false },
                { text: 'Добавить массовые действия', completed: false, selected: false },
                { text: 'Протестировать приложение', completed: false, selected: false },
                { text: 'Добавить сохранение в localStorage', completed: false, selected: false }
            ];
            renderTasks();
            saveTasks();
        }
    }

    // Инициализация примера задач через 0.5 сек после загрузки, если нет сохраненных
    setTimeout(initializeExampleTasks, 500);
