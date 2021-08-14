""" Modulul principal """
from teste import Teste
from prezentare import Consola
from business import ServiceStudent,ServiceProblema,ServiceNote,ServiceAsignareStudentProblema
from infrastructura import RepoStudentFile,RepoAsignareFile,RepoProblemaFile,RepoNoteFile
from validator import ValidatorStudent,ValidatorProblema,ValidatorNote
teste=Teste()
teste.run_tests()
fisier="fisier_studenti.txt"
repo_student=RepoStudentFile(fisier)
repo_problema=RepoProblemaFile()
repo_note=RepoNoteFile()
repo_asig=RepoAsignareFile()
valid_student=ValidatorStudent()
valid_problema=ValidatorProblema()
valid_note=ValidatorNote()
srv_student=ServiceStudent(repo_student,valid_student)
srv_problema=ServiceProblema(repo_problema,valid_problema)
srv_note=ServiceNote(repo_student,repo_problema,repo_note,valid_note)
srv_asig=ServiceAsignareStudentProblema(repo_student,repo_problema,repo_asig)
ui=Consola(srv_student,srv_problema,srv_note,srv_asig)
ui.run()
