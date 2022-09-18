----------------------------------
-- BATCH_JOB_INSTANCE
----------------------------------
INSERT INTO public.batch_job_instance (job_instance_id, version, job_name, job_key)
VALUES (1, 0, 'mmlMangaRetrieverJob', '801658d2049b39421f9b1d6ba9705a5c');

----------------------------------
-- BATCH_JOB_EXECUTION
----------------------------------
INSERT INTO public.batch_job_execution (job_execution_id, version, job_instance_id, create_time, start_time, end_time,
                                        status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES (1, 2, 1, '2022-09-18 17:04:13.186000', '2022-09-18 17:04:13.221000', '2022-09-18 17:04:13.263000', 'COMPLETED',
        'COMPLETED', '', '2022-09-18 17:04:13.264000', null);

----------------------------------
-- BATCH_JOB_EXECUTION_CONTEXT
----------------------------------
INSERT INTO public.batch_job_execution_context (job_execution_id, short_context, serialized_context)
VALUES (1, '{"@class":"java.util.HashMap"}', null);

----------------------------------
-- BATCH_JOB_EXECUTION_PARAMS
----------------------------------
INSERT INTO public.batch_job_execution_params (job_execution_id, type_cd, key_name, string_val, date_val, long_val,
                                               double_val, identifying)
VALUES (1, 'LONG', 'run.id', '', '1970-01-01 01:00:00.000000', 1663513453173, 0, 'Y');

----------------------------------
-- BATCH_STEP_EXECUTION
----------------------------------
INSERT INTO public.batch_step_execution (step_execution_id, version, step_name, job_execution_id, start_time, end_time,
                                         status, commit_count, read_count, filter_count, write_count, read_skip_count,
                                         write_skip_count, process_skip_count, rollback_count, exit_code, exit_message,
                                         last_updated)
VALUES (1, 3, 'startStep', 1, '2022-09-18 17:04:13.238000', '2022-09-18 17:04:13.257000', 'COMPLETED', 1, 0, 0, 0, 0, 0,
        0, 0, 'COMPLETED', '', '2022-09-18 17:04:13.257000');

----------------------------------
-- BATCH_STEP_EXECUTION_CONTEXT
----------------------------------
INSERT INTO public.batch_step_execution_context (step_execution_id, short_context, serialized_context)
VALUES (1,
        '{"@class":"java.util.HashMap","batch.taskletType":"com.wd.bym.batch.job.MmlMangaRetriever.MmlMangaRetrieverJobConfiguration$$Lambda$1095/0x00000008014697f0","batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}',
        null);

----------------------------------
-- UPDATE SEQUENCES
----------------------------------
SELECT SETVAL('batch_job_seq', (SELECT MAX(job_instance_id) FROM batch_job_instance));
SELECT SETVAL('batch_job_execution_seq', (SELECT MAX(job_execution_id) FROM batch_job_execution));
SELECT SETVAL('batch_step_execution_seq', (SELECT MAX(step_execution_id) FROM batch_step_execution));