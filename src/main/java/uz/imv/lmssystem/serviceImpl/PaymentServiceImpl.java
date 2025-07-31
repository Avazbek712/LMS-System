package uz.imv.lmssystem.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.imv.lmssystem.dto.PaymentCreateRequest;
import uz.imv.lmssystem.dto.PaymentCreateResponse;
import uz.imv.lmssystem.dto.PaymentStatusResponse;
import uz.imv.lmssystem.dto.response.PageableDTO;
import uz.imv.lmssystem.entity.Payment;
import uz.imv.lmssystem.dto.PaymentDTO;
import uz.imv.lmssystem.entity.Student;
import uz.imv.lmssystem.entity.User;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.exceptions.EntityNotFoundException;
import uz.imv.lmssystem.mapper.PaymentMapper;
import uz.imv.lmssystem.repository.PaymentRepository;
import uz.imv.lmssystem.repository.StudentRepository;
import uz.imv.lmssystem.repository.UserRepository;
import uz.imv.lmssystem.service.PaymentService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * Created by Avazbek on 29/07/25 11:10
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


    @Override
    @Transactional
    public PaymentCreateResponse create(PaymentCreateRequest request) {

        Student student = studentRepository.
                findById(
                        request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student with ID : " + request.getStudentId() + " not found!")
                );


        User employee = userRepository.findById(request.getCashierId())
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID : " + request.getCashierId() + " not found!"));


        if (request.getAmount().compareTo(student.getGroup().getCourse().getPrice()) < 0) {
            throw new IllegalArgumentException("The amount is less than the price of the course!");
        }

        LocalDate paidUntil = student.getPaidUntilDate();
        YearMonth requestedMonth = request.getPaymentFor();

        if (paidUntil != null && (YearMonth.from(paidUntil).isAfter(requestedMonth) || YearMonth.from(paidUntil).equals(requestedMonth))) {
            throw new IllegalArgumentException("This period (" + requestedMonth + ") or more later period already has been paid. Paid until : " + paidUntil);
        }

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setAmount(request.getAmount());
        payment.setCashier(employee);


        paymentRepository.save(payment);

        LocalDate newPaidUntilDate = request.getPaymentFor().atEndOfMonth();
        student.setPaidUntilDate(newPaidUntilDate);
        student.setPaymentStatus(true);

        return new PaymentCreateResponse(
                student.getName(),
                student.getSurname(),
                request.getAmount(),
                request.getPaymentFor()
        );
    }


    @Override
    @Transactional
    public void delete(Long id) {
        paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment with ID : " + id + " not found!"));

        paymentRepository.deleteById(id);

        Student student = paymentRepository.getStudentById(id);

        student.setPaymentStatus(false);
    }

    @Override
    public PaymentStatusResponse checkPaymentStatus(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with ID : " + studentId + " not found!"));

        return new PaymentStatusResponse(
                student.getName(),
                student.getSurname(),
                student.getPaymentStatus()
        );
    }

    @Override
    public PageableDTO getAll(Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Payment> payments = paymentRepository.findAll(pageable);
        List<Payment> paymentList = payments.getContent();

        if (paymentList.isEmpty())
            return new PageableDTO(size, 0L, 0, false, false, null);

        List<PaymentDTO> paymentDTOS = paymentMapper.toDTO(paymentList);

        return new PageableDTO(
                payments.getSize(),
                payments.getTotalElements(),
                payments.getTotalPages(),
                !payments.isLast(),
                !payments.isFirst(),
                paymentDTOS
        );
    }

    @Override
    public PaymentDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment with ID : " + id + " not found!"));

        return paymentMapper.toDto(payment);
    }
}
