package jp.kobespiral.nakata.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.nakata.todo.dto.MemberForm;
import jp.kobespiral.nakata.todo.entity.Member;
import jp.kobespiral.nakata.todo.exception.ToDoAppException;
import jp.kobespiral.nakata.todo.repository.MemberRepository;

@Service
public class MemberService {
   @Autowired
   MemberRepository mRepo;
   /**
    * メンバーを作成する (C)
    * @param form
    * @return
    */
   public Member createMember(MemberForm form) {
       //IDの重複チェック
       String mid = form.getMid();
       if (mRepo.existsById(mid)) {
           throw new ToDoAppException(ToDoAppException.MEMBER_ALREADY_EXISTS, mid + ": Member already exists");
       }
       Member m = form.toEntity();
       return mRepo.save(m);
   }
   /**
    * メンバーを取得する (R)
    * @param mid
    * @return
    */
   public Member getMember(String mid) {
       Member m = mRepo.findById(mid).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, mid + ": No such member exists"));
       return m;
   }
   /**
    * 全メンバーを取得する (R)
    * @return
    */
   public List<Member> getAllMembers() {
       return mRepo.findAll();
   }
   /**
    * メンバーを削除する (D)
    */
   public void deleteMember(String mid) {
       Member m = getMember(mid);
       mRepo.delete(m);
   }
}