# AI Tool Usage Record

**Project:** Eric Chatbot (CS2103/T)
**Last Updated:** February 17, 2026

---

## Session 1: Code Quality Evaluation & Refactoring

**Tool:** GitHub Copilot

**What Was Done:**

**Code Analysis & Evaluation**
   - Analyzed 20 Java files for SLAP and coding standard compliance
   - Generated 4 comprehensive evaluation reports
   - Identified 5 files with violations (2 critical, 3 minor)

**Comment Improvements (All Files)**
   - Updated Javadoc for all refactored methods
   - Added "High-level," "Mid-level," and "Low-level" comments to explain abstraction
   - Added method parameter documentation (@param tags)
   - Added return value documentation (@return tags)
   - Added exception documentation (@throws tags)
   - Improved inline comments for clarity
   - Removed redundant comments

**Files Modified:**
- AddTaskCommand.java (new abstract base class)
- AddTodoCommand.java (refactored, 43 → 27 lines)
- AddDeadlineCommand.java (refactored, 43 → 27 lines)
- AddEventCommand.java (refactored, 43 → 27 lines)
- Parser.java (major refactoring + constructor)
- TaskList.java (major refactoring)
- FindCommand.java (minor refactoring)
- Repository.java (minor refactoring)
- Task.java (minor refactoring)
- Eric.java (updated to use Parser instance)
- Main.java (improved error handling comments)

**Challenges & Solutions:**
- Making sure that the refactored code achieved what I desired and having to check
through every change made.

**Key Observations:**
- Copilot excels at identifying code quality issues and suggesting improvements
- Generated documentation is professional and follows Java conventions
- All refactoring changes maintained backward compatibility
- Comments and documentation follow CS2103 course standards
- SLAP compliance better achieved across all 20 files

---






