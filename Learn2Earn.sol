pragma solidity ^0.4.0;
contract Learn2Earn {

    struct Bet {
        uint weiValue;
        uint8 minimumGrade;
        address parent;
        address student;
        address teacher;
        bool confirmed;
    }
    
    mapping(string => Bet) betUidToBet;

    function Learn2Earn() public {
    }
    
    function createBet(string betUid, uint8 minimumGrade, address student, 
                       address parent, address teacher) public payable {
        betUidToBet[betUid] = Bet(msg.value, minimumGrade, parent, student, teacher, false);
    }
    
    function confirmBet(string betUid) public payable {
        // Only allow the parent to confirm a bet
        if (betUidToBet[betUid].parent != msg.sender) return;
        betUidToBet[betUid].confirmed = true;
	betUidToBet[betUid].weiValue = betUidToBet[betUid].weiValue + msg.value;
    }
    
    function endBet(string betUid, uint8 studentGrade) public {
        // Only teachers are allowed to end bets
        if (betUidToBet[betUid].teacher != msg.sender) return;
        
        if (betUidToBet[betUid].confirmed == false) {
            // Parent never confirmed the bet so return the money back to the student
            betUidToBet[betUid].student.transfer(betUidToBet[betUid].weiValue);
            return;
        } 
        
        if (studentGrade >= betUidToBet[betUid].minimumGrade) {
            // Student won the bet
            betUidToBet[betUid].student.transfer(betUidToBet[betUid].weiValue);
        } else {
            // Parent won the bet
            betUidToBet[betUid].parent.transfer(betUidToBet[betUid].weiValue);
        }
    }
    
}
