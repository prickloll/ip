# AI Tool Usage Record

**Project:** Eric Chatbot (CS2103)
**Last Updated:** February 19, 2026

---

## Code Quality Evaluation, Refactoring and Junit Test Generation

**Tool:** GitHub Copilot

**What Was Done:**

**Code Analysis & Evaluation**
   - Analyzed 20 Java files for SLAP and coding standard compliance
   - Identified 5 files with violations

**Comment Improvements (All Files)**
   - Updated Javadoc for all refactored methods
   - Added method parameter documentation (@param tags)
   - Added return value documentation (@return tags)
   - Added exception documentation (@throws tags)
   - Improved inline comments for clarity
   - Removed redundant comments

**Files Modified:**
- AddTaskCommand.java (new abstract base class)
- AddTodoCommand.java (refactored)
- AddDeadlineCommand.java (refactored)
- AddEventCommand.java (refactored)
- Parser.java (major refactoring + constructor)
- TaskList.java (major refactoring)
- FindCommand.java (minor refactoring)
- Repository.java (minor refactoring)
- Task.java (minor refactoring)
- Eric.java (updated to use Parser instance)
- Main.java (improved error handling comments)
- Ui.java (improved user interaction comments)
- ParserTest.java (added Junit tests for new Parser constructor and refactored parse methods)
- RepositoryTest.java (added Junit tests for refactored Repository save and load methods)
- TaskFileParsingTest.java (added Junit tests for refactored Task file parsing logic)
- DeadlineTest.java (added Junit tests for refactored Deadline parsing logic)
- README.md (created user guide)

**Challenges & Solutions:**
- Making sure that the refactored code achieved what I desired and having to check through every change made.
- Junit test generated at times did not match the output of the refactored code, so I had to made sure they do.
- When it made changes, it sometimes introduced new errors that I had to fix manually which was time-consuming.

**Key Observations:**
- Copilot excels at identifying code quality issues and suggesting improvements
- Generated documentation is professional and follows Java conventions
- All refactoring changes maintained backward compatibility
- Comments and documentation follow CS2103 course standards
- SLAP compliance and other code quality improvement were successfully achieved
- Mass creation of Junit for specific methods specified
